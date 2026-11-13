package com.music.bitchord.sponsorblock

/**
 * SponsorBlock segment categories supported for auto-skipping.
 *
 * Covers music tracks as well as podcasts and long-form video streams.
 */
enum class SponsorBlockCategory(
    val key: String,
    val title: String,
    val description: String,
    val defaultEnabled: Boolean,
) {
    SPONSOR(
        key = "sponsor",
        title = "Sponsor",
        description = "Paid promotion, paid referrals, and direct ads",
        defaultEnabled = true,
    ),
    SELF_PROMO(
        key = "selfpromo",
        title = "Self Promotion",
        description = "Promotions for creator's merch, social links, or personal products",
        defaultEnabled = true,
    ),
    INTERACTION(
        key = "interaction",
        title = "Interaction Reminder",
        description = "Reminders to subscribe, like, comment, or follow",
        defaultEnabled = true,
    ),
    INTRO(
        key = "intro",
        title = "Intro / Intermission",
        description = "Intro animation, title sequence, or intermission",
        defaultEnabled = false,
    ),
    OUTRO(
        key = "outro",
        title = "Outro / End Cards",
        description = "End credits, end cards, or preview of other content",
        defaultEnabled = false,
    ),
    MUSIC_OFFTOPIC(
        key = "music_offtopic",
        title = "Non-Music Section",
        description = "Non-music dialogue, skits, or silence in music tracks and videos",
        defaultEnabled = true,
    ),
    PREVIEW(
        key = "preview",
        title = "Preview / Recap",
        description = "Recap of previous episodes or teaser of what is coming up",
        defaultEnabled = false,
    ),
    FILLER(
        key = "filler",
        title = "Filler / Tangent",
        description = "Tangents, banter, or filler discussions (common in podcasts)",
        defaultEnabled = false,
    );

    companion object {
        fun fromKey(key: String): SponsorBlockCategory? =
            entries.firstOrNull { it.key.equals(key, ignoreCase = true) }
    }
}
