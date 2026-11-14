<div align="center">

# PureChord
a fork of

<br/>

<img src="Logo.png" alt="PureChord app icon" width="200" />

<br/>
<br/>

[![Latest release](https://img.shields.io/github/v/release/Deeptanil/PureChord?style=for-the-badge&labelColor=0d1117)](https://github.com/Deeptanil/PureChord/releases)
[![License](https://img.shields.io/github/license/Deeptanil/PureChord?style=for-the-badge&labelColor=0d1117)](https://github.com/Deeptanil/PureChord/blob/custom-sponsorblock/LICENSE)
[![Downloads](https://img.shields.io/github/downloads/Deeptanil/PureChord/total?style=for-the-badge&labelColor=0d1117)](https://github.com/Deeptanil/PureChord/releases)
[![Upstream](https://img.shields.io/badge/Fork_of-BitChord-blue?style=for-the-badge&labelColor=0d1117)](https://github.com/kushagrasinghx/BitChord)

<br/>

[**Download**](#download) · [**Features**](#features) · [**Support**](#support) · [**Disclaimer**](#disclaimer) · [**License**](#license)

<br/>

<a href="https://fmhy.net/mobile#youtube-music" target="_blank" rel="noopener noreferrer"><img src="https://cdn.jsdelivr.net/gh/JMcrafter26/badges@main/src/assets/available/fmhy/cozy.svg" alt="Featured on FMHY" height="55"/></a>
<a href="https://trendshift.io/repositories/177639?utm_source=repository-badge&amp;utm_medium=badge&amp;utm_campaign=badge-repository-177639" target="_blank" rel="noopener noreferrer"><img src="https://trendshift.io/api/badge/repositories/177639" alt="kushagrasinghx%2FBitChord | Trendshift" width="250" height="55"/></a>

</div>

> [!NOTE]
> **About this fork:** **PureChord** is a customized personal fork of [BitChord](https://github.com/kushagrasinghx/BitChord) by [Kushagra Singh](https://github.com/kushagrasinghx). It enhances BitChord with seamless SponsorBlock integration across music tracks, podcasts, and video playback alongside musical gap preservation. All core application architecture, design, and playback infrastructure belong to Kushagra Singh and upstream contributors.

> [!IMPORTANT]
> ### 🌟 Credits & Acknowledgments
> 
> * **Original BitChord Author:** Huge gratitude and full credit to [Kushagra Singh](https://github.com/kushagrasinghx) and all [BitChord contributors](https://github.com/kushagrasinghx/BitChord/graphs/contributors) for creating the phenomenal [BitChord](https://github.com/kushagrasinghx/BitChord) music player, its exquisite Apple-inspired UI, and underlying architecture. This fork claims no credit for the core application and wholeheartedly encourages supporting Kushagra Singh's work via [Ko-fi](https://ko-fi.com/kushagrasinghx) and [PayPal](https://paypal.me/kuxhagrasingh).
> * **SponsorBlock Project & Community:** Tremendous credit and thanks to [Ajay Ramachandran](https://github.com/ajayyy) and the open-source [SponsorBlock](https://sponsor.ajay.app) team, developers, and crowd-sourced database contributors who make community segment skipping possible.
> * **Notice:** This fork exists solely to provide SponsorBlock integration for personal use and community experiments. All credit for the underlying technologies belongs entirely to their original creators and teams.

---

<div align="center">

<img src="Banner.png" alt="BitChord banner" width="100%" />

<h1><a id="features"></a>Features</h1>

<table>
  <tr>
    <td width="50%" valign="top">

#### Playback
- **Search, browse and play** anything available on YouTube Music.
- **Hi-Res lossless audio** — FLAC/ALAC from a configured module source, with YouTube Music as fallback.
- **Gapless playback with true crossfade**, adjustable 0–12s.
- **Automix [Beta]** — DJ-style transitions with beat-matching and tempo-stretching.
- **Offline downloads** — save tracks with embedded metadata.
- **Local music library** integration.
- **Background playback** via a proper foreground media session.
- **Apple-like lyrics animation** — credit to [binimum](https://github.com/binimum/am-lyrics).

#### Connectivity & Accounts
- **Sign in with your Google account** for personalized content.
- **Spotify integration** — connect your account to play your playlists and Liked Songs.
- **Discord Rich Presence** — in-app login, live track/artist/album and progress.
- **Scrobbling** to Last.fm and ListenBrainz.
- **Pluggable sources** — add, edit, test and health-check module sources.

    </td>
    <td width="50%" valign="top">

#### Experience
- **Animated album canvas** — motion artwork on the now-playing screen.
- **Word-synced lyrics** — word/syllable-level highlighting from multiple sources.
- **Lyrics providers** — credit to [lrc.red](https://lrc.red), [BiniLyrics](https://github.com/binimum), [BetterLyrics](https://github.com/better-lyrics/better-lyrics), [PaxSenix](https://lyrics.paxsenix.org), [LyricsPlus](https://github.com/ibratabian17/YouLyPlus), [SimpMusic](https://github.com/maxrave-dev/SimpMusic), [Unison](https://unison.boidu.dev), [Megalobiz](https://www.megalobiz.com), [KuGou](https://www.kugou.com), [LRCLIB](https://lrclib.net), [Musixmatch](https://www.musixmatch.com) and [Genius](https://genius.com).
- **Dynamic, artwork-driven theming** — Material palette extracted from album art.
- **Frosted-glass UI** — Telegram-style translucent bars via Haze, Material 3 theming.

#### Controls & Tweaks
- **SponsorBlock & Musical Gap Protection** — automatically skips non-music sections (sponsors, self-promos, interaction reminders, intros, outros, off-topic dialogue) directly through the unified "Skip silence & non-music" toggle. Features smart seek detection (restarting skips intro; scrubbing inside whitelists segment) and raised 3.5s silence floor to preserve dramatic musical pauses (e.g., Adele's *Skyfall*).
- **Per-network audio quality** — separate quality ceilings for Wi-Fi and mobile data.
- **Playback speed control** (0.5×–2.0×) and **skip silence**.
- **Sleep timer** — fixed presets or "stop after this track".
- **System equalizer** integration.
- **Bit-perfect USB exclusive output** — sends lossless tracks to a USB DAC unchanged, at each track's own rate and bit depth. Thanks to [Mornye](https://github.com/mornyeapp/Mornye) for the reference.
- **Stats for nerds** — codec, bit depth, sample rate, and more on the now-playing screen.

    </td>
  </tr>
</table>

</div>

---

<div align="center">

<h1><a id="download"></a>Download</h1>

<a href="https://github.com/Deeptanil/PureChord/releases/latest" target="_blank" rel="noopener noreferrer"><img src="https://cdn.jsdelivr.net/gh/JMcrafter26/badges@main/src/assets/supported/android/cozy.svg" alt="Download for Android" height="55"/></a>

Grab the latest signed APK (`PureChord-v1.9.1-sb.apk` or lighter `PureChord-v1.9.1-sb-arm64-v8a.apk`) from our [Releases](https://github.com/Deeptanil/PureChord/releases) page. Sideloading requires enabling "Install unknown apps" for whichever app you download it with.


</div>

---

<div align="center">

<h1><a id="support"></a>Support</h1>

PureChord is a free, non-monetized fork. **These support links belong entirely to Kushagra Singh (original author of BitChord); all donations and financial contributions go directly to him, not to me.** If PureChord or BitChord has earned a spot in your rotation, please consider supporting Kushagra's incredible work:

<br/>

<a href="https://ko-fi.com/kushagrasinghx" target="_blank" rel="noopener noreferrer"><img src="https://cdn.jsdelivr.net/gh/JMcrafter26/badges@main/src/assets/donate/kofi-singular-alt/cozy.svg" alt="Support Kushagra on Ko-fi" height="55"/></a>
<a href="https://paypal.me/kuxhagrasingh" target="_blank" rel="noopener noreferrer"><img src="https://cdn.jsdelivr.net/gh/JMcrafter26/badges@main/src/assets/donate/paypal-plural/cozy.svg" alt="Support Kushagra on PayPal" height="55"/></a>

<br/>
<img src="upi_support.jpg" alt="UPI Support for Kushagra Singh" width="250" />

</div>

---

<div align="center">

<h1><a id="disclaimer"></a>Disclaimer & Legal Notice</h1>

PureChord is an independent, community-driven third-party audio player and fork of BitChord. It is **not** associated with, endorsed by, or affiliated with Google LLC, YouTube, YouTube Music, Deezer, Telegram, or any of their parent companies.

* **No Media Hosting:** PureChord does not host, upload, or store copyrighted music files. It operates strictly as an offline-first client interface to scan local device storage or stream media directly from public, public-facing, or user-authenticated APIs.
* **Fair Use & API Usage:** This software is created solely for personal research, educational, and fair-use purposes. Users are entirely responsible for ensuring their usage aligns with their local copyright laws and YouTube Terms of Service.
* **SponsorBlock Data:** Skip segments are crowd-sourced and submitted by the public community via the open-source [SponsorBlock](https://sponsor.ajay.app) database. PureChord does not host or curate user-submitted timestamps.
* **Copyleft (GPLv3):** As a derivative work of BitChord, PureChord is free software licensed under the GNU General Public License v3.0 (GPLv3). Complete corresponding source code is freely available in this repository under the same license.

</div>

---

<div align="center">

<h1><a id="license"></a>License</h1>

PureChord is licensed under the **GNU General Public License v3.0 (GPLv3)** in compliance with the original BitChord license. See the [LICENSE](LICENSE) file for complete details.

Copyright (C) 2024–2026 Kushagra Singh (BitChord core)  
Copyright (C) 2026 PureChord contributors

</div>
