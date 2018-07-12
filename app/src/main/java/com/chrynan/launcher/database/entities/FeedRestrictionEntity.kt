package com.chrynan.launcher.database.entities

import com.chrynan.launcher.util.PackageName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class FeedRestrictionEntity(
        val type: Int,
        val packageName: PackageName? = null,
        val intentType: String? = null
) : DatabaseEntity {

    companion object {

        fun newPackage(packageName: PackageName) = FeedRestrictionEntity(type = TYPE_PACKAGE, packageName = packageName)

        fun newIntent(intentType: String) = FeedRestrictionEntity(type = TYPE_INTENT, intentType = intentType)

        const val TYPE_PACKAGE = 0
        const val TYPE_INTENT = 1
    }

    @Id
    override var databaseId: Long = 0
}