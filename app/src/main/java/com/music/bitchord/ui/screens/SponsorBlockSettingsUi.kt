package com.music.bitchord.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.music.bitchord.sponsorblock.SponsorBlockCategory
import com.music.bitchord.sponsorblock.SponsorBlockSettings
import com.music.bitchord.ui.icons.SfIcons

/**
 * Modular Settings UI for SponsorBlock configuration and category toggles.
 * Kept in its own file to maintain minimal git diff with upstream SettingsSheet.kt.
 */
internal fun SettingsGroupScope.SponsorBlockSettingsRows() {
    val title = "SponsorBlock"
    row(title, "sponsor", "sponsorblock", "skip", "ad", "promo") {
        val enabled by SponsorBlockSettings.enabled.collectAsState()
        SettingsRow(
            icon = SfIcons.Extension,
            title = title,
            subtitle = "Automatically skip sponsors, promos, and off-topic segments",
            trailing = {
                IosSwitch(
                    checked = enabled,
                    onCheckedChange = { SponsorBlockSettings.setEnabled(it) },
                )
            },
            onClick = { SponsorBlockSettings.setEnabled(!enabled) },
        )
    }

    val applyToMediaTitle = "Apply to Podcasts & Video Streams"
    row(applyToMediaTitle, "podcast", "video", "long-form", "stream", "sponsor", divided = false) {
        val sbEnabled by SponsorBlockSettings.enabled.collectAsState()
        val applyToPodcasts by SponsorBlockSettings.applyToPodcastsAndVideos.collectAsState()
        AnimatedVisibility(visible = sbEnabled) {
            Column {
                SettingsSubRow(
                    title = applyToMediaTitle,
                    subtitle = "Skip sponsored segments in podcasts and long-form video streams",
                    checked = applyToPodcasts,
                    onCheckedChange = { SponsorBlockSettings.setApplyToPodcastsAndVideos(it) },
                )
            }
        }
    }

    for (category in SponsorBlockCategory.entries) {
        row(category.title, "sponsor", category.key, category.description, divided = false) {
            val sbEnabled by SponsorBlockSettings.enabled.collectAsState()
            val categoryEnabled by SponsorBlockSettings.categoryState(category).collectAsState()
            AnimatedVisibility(visible = sbEnabled) {
                Column {
                    SettingsSubRow(
                        title = category.title,
                        subtitle = category.description,
                        checked = categoryEnabled,
                        onCheckedChange = { SponsorBlockSettings.setCategoryEnabled(category, it) },
                    )
                }
            }
        }
    }
}
