package com.chrynan.launcher.model

import android.content.pm.LauncherApps.ShortcutQuery.*

enum class ShortcutType(val queryFlag: Int) {

    DYNAMIC(FLAG_MATCH_DYNAMIC),
    STATIC(FLAG_MATCH_MANIFEST),
    PINNED(FLAG_MATCH_PINNED)
}