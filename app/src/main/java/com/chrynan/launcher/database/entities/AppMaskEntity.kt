package com.chrynan.launcher.database.entities

import com.chrynan.launcher.util.PackageName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class AppMaskEntity(
        val packageName: PackageName,
        val maskedName: String,
        val maskedIconFileName: String
) : DatabaseEntity {

    @Id
    override var databaseId: Long = 0
}