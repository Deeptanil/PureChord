package com.music.bitchord.sponsorblock

import android.util.Log
import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Coordinates SponsorBlock segment detection and auto-skipping.
 *
 * Interacts cleanly with [PlaybackService] and [ExoPlayer].
 * Supports music tracks, podcasts, and long-form video playback.
 */
object SponsorBlockManager {

    private const val TAG = "SponsorBlock"
    private const val TICKER_INTERVAL_MS = 300L
    private const val SKIP_LEAD_MS = 30L

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var currentVideoId: String? = null
    private var isPodcastOrVideoContent: Boolean = false
    private var activeSegments: List<SponsorSegment> = emptyList()

    private var fetchJob: Job? = null
    private var tickerJob: Job? = null

    private var lastSkippedUuid: String? = null
    private var lastSkipTimeMs: Long = 0L

    private val _lastSkippedSegment = MutableStateFlow<SponsorSegment?>(null)
    val lastSkippedSegment = _lastSkippedSegment.asStateFlow()

    private val _skipEvents = MutableSharedFlow<SponsorSegment>(extraBufferCapacity = 8)
    val skipEvents = _skipEvents.asSharedFlow()

    /**
     * Called when the active track changes in the media player.
     *
     * @param videoId The YouTube video ID of the current track/stream.
     * @param isVideo True if the media is a video stream or podcast.
     * @param playerProvider Lambda returning the active ExoPlayer instance.
     */
    fun onTrackChanged(
        videoId: String?,
        isVideo: Boolean = false,
        playerProvider: () -> Player?,
    ) {
        fetchJob?.cancel()
        fetchJob = null
        activeSegments = emptyList()
        lastSkippedUuid = null
        _lastSkippedSegment.value = null

        val cleanId = videoId?.trim().orEmpty()
        // Check for valid YouTube video IDs (alphanumeric with '-' and '_', typically 11 chars)
        val isYtVideoId = cleanId.isNotEmpty() &&
            cleanId.length == 11 &&
            !cleanId.contains("/") &&
            !cleanId.contains(":")

        if (!isYtVideoId) {
            currentVideoId = null
            return
        }

        currentVideoId = cleanId
        isPodcastOrVideoContent = isVideo

        if (!SponsorBlockSettings.enabled.value) return
        if (isPodcastOrVideoContent && !SponsorBlockSettings.applyToPodcastsAndVideos.value) return

        fetchJob = scope.launch {
            val segments = SponsorBlockClient.getSegments(
                videoId = cleanId,
                categories = SponsorBlockSettings.getActiveCategories(),
            )
            if (currentVideoId == cleanId) {
                activeSegments = segments
                val player = playerProvider()
                if (player != null && player.isPlaying) {
                    checkAndSkip(player.currentPosition) { target ->
                        player.seekTo(target)
                    }
                }
            }
        }
    }

    /**
     * Called when the player transitions between playing and paused states.
     */
    fun onIsPlayingChanged(isPlaying: Boolean, playerProvider: () -> Player?) {
        tickerJob?.cancel()
        tickerJob = null

        if (isPlaying && SponsorBlockSettings.enabled.value) {
            tickerJob = scope.launch {
                while (isActive) {
                    val player = playerProvider()
                    if (player == null || !player.isPlaying) break
                    checkAndSkip(player.currentPosition) { target ->
                        player.seekTo(target)
                    }
                    delay(TICKER_INTERVAL_MS)
                }
            }
        }
    }

    /**
     * Inspects [currentPositionMs] against loaded segments.
     * If within a segment of an enabled category, invokes [seekTo] to jump past it.
     */
    fun checkAndSkip(
        currentPositionMs: Long,
        seekTo: (targetMs: Long) -> Unit,
    ) {
        if (!SponsorBlockSettings.enabled.value) return
        if (isPodcastOrVideoContent && !SponsorBlockSettings.applyToPodcastsAndVideos.value) return
        if (activeSegments.isEmpty()) return

        val now = System.currentTimeMillis()
        for (segment in activeSegments) {
            if (!SponsorBlockSettings.isCategoryEnabled(segment.category)) continue

            if (segment.contains(currentPositionMs)) {
                // Prevent repeated seeks if already at target or recently skipped
                if (lastSkippedUuid == segment.uuid && abs(now - lastSkipTimeMs) < 1500L) {
                    continue
                }

                val targetMs = segment.endMs + SKIP_LEAD_MS
                Log.i(
                    TAG,
                    "Auto-skipping ${segment.category.title} segment " +
                        "(${segment.startMs}ms -> ${segment.endMs}ms) to ${targetMs}ms",
                )
                lastSkippedUuid = segment.uuid
                lastSkipTimeMs = now
                _lastSkippedSegment.value = segment
                _skipEvents.tryEmit(segment)

                seekTo(targetMs)
                break
            }
        }
    }

    /**
     * Clears all segment state.
     */
    fun reset() {
        fetchJob?.cancel()
        fetchJob = null
        tickerJob?.cancel()
        tickerJob = null
        currentVideoId = null
        activeSegments = emptyList()
        lastSkippedUuid = null
        _lastSkippedSegment.value = null
    }
}
