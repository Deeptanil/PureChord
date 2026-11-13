package com.music.bitchord.sponsorblock

import android.util.Log
import android.util.LruCache
import com.music.bitchord.data.Http
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request
import org.json.JSONArray

/**
 * Client for fetching SponsorBlock skip segments.
 *
 * Segments are cached in-memory using an LRU cache to avoid redundant API hits.
 */
object SponsorBlockClient {

    private const val TAG = "SponsorBlock"
    private val segmentCache = LruCache<String, List<SponsorSegment>>(64)

    /**
     * Fetches segments for [videoId] matching the currently enabled categories.
     * Returns empty list if no segments exist (e.g. 404) or on network failure.
     */
    suspend fun getSegments(
        videoId: String,
        categories: List<SponsorBlockCategory> = SponsorBlockSettings.getActiveCategories(),
    ): List<SponsorSegment> = withContext(Dispatchers.IO) {
        if (videoId.isBlank() || categories.isEmpty()) return@withContext emptyList()

        val cacheKey = "${videoId}_${categories.joinToString(",") { it.key }}"
        segmentCache.get(cacheKey)?.let { return@withContext it }

        val baseUrl = SponsorBlockSettings.apiUrl.value.toHttpUrlOrNull() ?: return@withContext emptyList()
        val categoriesJson = JSONArray(categories.map { it.key }).toString()

        val url = baseUrl.newBuilder()
            .addPathSegment("api")
            .addPathSegment("skipSegments")
            .addQueryParameter("videoID", videoId)
            .addQueryParameter("categories", categoriesJson)
            .build()

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "BitChord-Android-SponsorBlock")
            .get()
            .build()

        try {
            Http.client.newCall(request).execute().use { response ->
                if (response.code == 404) {
                    // No segments submitted for this video
                    segmentCache.put(cacheKey, emptyList())
                    return@withContext emptyList()
                }
                if (!response.isSuccessful) {
                    Log.w(TAG, "SponsorBlock API returned HTTP ${response.code} for $videoId")
                    return@withContext emptyList()
                }

                val body = response.body?.string().orEmpty()
                if (body.isBlank()) return@withContext emptyList()

                val jsonArray = JSONArray(body)
                val segments = mutableListOf<SponsorSegment>()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.optJSONObject(i) ?: continue
                    val categoryStr = obj.optString("category")
                    val category = SponsorBlockCategory.fromKey(categoryStr) ?: continue
                    val segmentArray = obj.optJSONArray("segment") ?: continue
                    if (segmentArray.length() < 2) continue

                    val startSec = segmentArray.optDouble(0, 0.0)
                    val endSec = segmentArray.optDouble(1, 0.0)
                    if (endSec <= startSec) continue

                    val startMs = (startSec * 1000).toLong()
                    val endMs = (endSec * 1000).toLong()
                    val uuid = obj.optString("UUID", "")

                    segments.add(
                        SponsorSegment(
                            uuid = uuid,
                            category = category,
                            startMs = startMs,
                            endMs = endMs,
                        )
                    )
                }

                val sorted = segments.sortedBy { it.startMs }
                segmentCache.put(cacheKey, sorted)
                Log.d(TAG, "Loaded ${sorted.size} SponsorBlock segments for $videoId")
                sorted
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to fetch SponsorBlock segments for $videoId: ${e.message}")
            emptyList()
        }
    }

    fun clearCache() {
        segmentCache.evictAll()
    }
}
