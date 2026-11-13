package com.music.bitchord.sponsorblock

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Settings and persistence for SponsorBlock functionality.
 *
 * Keeps configuration modular and self-contained to prevent merge conflicts
 * with upstream BitChord updates.
 */
object SponsorBlockSettings {

    private const val PREFS_NAME = "bitchord_sponsorblock_settings"
    private const val KEY_ENABLED = "sb_enabled"
    private const val KEY_APPLY_PODCASTS_VIDEOS = "sb_apply_podcasts_videos"
    private const val KEY_API_URL = "sb_api_url"
    private const val DEFAULT_API_URL = "https://sponsor.ajay.app"

    private lateinit var prefs: SharedPreferences

    val enabled = MutableStateFlow(true)
    val applyToPodcastsAndVideos = MutableStateFlow(true)
    val apiUrl = MutableStateFlow(DEFAULT_API_URL)

    private val categoryFlows = SponsorBlockCategory.entries.associateWith { category ->
        MutableStateFlow(category.defaultEnabled)
    }

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        readAll()
    }

    private fun readAll() {
        if (!::prefs.isInitialized) return
        enabled.value = prefs.getBoolean(KEY_ENABLED, true)
        applyToPodcastsAndVideos.value = prefs.getBoolean(KEY_APPLY_PODCASTS_VIDEOS, true)
        apiUrl.value = prefs.getString(KEY_API_URL, DEFAULT_API_URL) ?: DEFAULT_API_URL

        for (category in SponsorBlockCategory.entries) {
            val isEnabled = prefs.getBoolean(
                categoryPrefKey(category),
                category.defaultEnabled,
            )
            categoryFlows[category]?.value = isEnabled
        }
    }

    fun setEnabled(value: Boolean) {
        enabled.value = value
        if (::prefs.isInitialized) {
            prefs.edit().putBoolean(KEY_ENABLED, value).apply()
        }
    }

    fun setApplyToPodcastsAndVideos(value: Boolean) {
        applyToPodcastsAndVideos.value = value
        if (::prefs.isInitialized) {
            prefs.edit().putBoolean(KEY_APPLY_PODCASTS_VIDEOS, value).apply()
        }
    }

    fun setApiUrl(value: String) {
        val sanitized = value.trim().trimEnd('/')
        apiUrl.value = sanitized
        if (::prefs.isInitialized) {
            prefs.edit().putString(KEY_API_URL, sanitized).apply()
        }
    }

    fun categoryState(category: SponsorBlockCategory): StateFlow<Boolean> =
        categoryFlows[category]?.asStateFlow() ?: MutableStateFlow(category.defaultEnabled).asStateFlow()

    fun isCategoryEnabled(category: SponsorBlockCategory): Boolean =
        categoryFlows[category]?.value ?: category.defaultEnabled

    fun setCategoryEnabled(category: SponsorBlockCategory, value: Boolean) {
        categoryFlows[category]?.value = value
        if (::prefs.isInitialized) {
            prefs.edit().putBoolean(categoryPrefKey(category), value).apply()
        }
    }

    fun getActiveCategories(): List<SponsorBlockCategory> =
        SponsorBlockCategory.entries.filter { isCategoryEnabled(it) }

    fun getActiveCategoryKeys(): List<String> =
        getActiveCategories().map { it.key }

    private fun categoryPrefKey(category: SponsorBlockCategory): String =
        "sb_category_${category.key}"
}
