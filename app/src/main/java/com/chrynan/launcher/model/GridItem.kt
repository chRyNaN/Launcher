package com.chrynan.launcher.model

import android.content.Intent
import android.content.pm.ShortcutInfo

sealed class GridItem(
        val columns: Int,
        val rows: Int,
        val position: Position = Position(0, 0)
) {

    data class Position(
            val column: Int,
            val row: Int
    )
}

sealed class LauncherItem(
        itemPosition: Position = Position(0, 0),
        val name: String,
        val badge: Badge? = null
) : GridItem(columns = 1, rows = 1, position = itemPosition) {

    sealed class SingleItem(
            name: String,
            badge: Badge? = null,
            itemPosition: Position = Position(0, 0)
    ) : LauncherItem(name = name, badge = badge, itemPosition = itemPosition) {

        data class App(
                val packageName: String,
                val launchIntent: Intent,
                val appMask: AppMask? = null,
                private val appName: String,
                private val appBadge: Badge? = null,
                private val appPosition: Position = Position(0, 0)
        ) : SingleItem(name = appName, badge = appBadge, itemPosition = appPosition)

        data class Shortcut(
                val launchIntent: Intent?,
                val shortcutInfo: ShortcutInfo, // One of these values or both will be useful
                private val shortcutName: String,
                private val shortcutBadge: Badge? = null,
                private val shortcutPosition: Position = Position(0, 0)
        ) : SingleItem(name = shortcutName, badge = shortcutBadge, itemPosition = shortcutPosition)
    }

    data class Folder(
            val items: List<SingleItem>,
            private val folderName: String,
            private val folderPosition: Position = Position(0, 0)
    ) : LauncherItem(name = folderName, itemPosition = folderPosition)
}

val LauncherItem?.hasBadge: Boolean
    get() = this?.badge?.enabled == true