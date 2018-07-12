package com.chrynan.launcher.database.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class FeedTypeEntity(
        val type: Int = TYPE_NONE,
        val url: String? = null
) : DatabaseEntity {

    companion object {

        fun newTypeNone() = FeedTypeEntity()

        fun newTypeWeb(url: String) = FeedTypeEntity(type = TYPE_WEB, url = url)

        fun newTypeApp() = FeedTypeEntity(type = TYPE_APP)

        const val TYPE_NONE = 0
        const val TYPE_WEB = 1
        const val TYPE_APP = 2
    }

    @Id
    override var databaseId: Long = 0
}