package com.chrynan.launcher.database.entities

import com.chrynan.launcher.database.converter.FeedWebSourceConverter
import com.chrynan.launcher.model.FeedWebSource
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class FeedSettingsEntity(
        @Convert(converter = FeedWebSourceConverter::class, dbType = Int::class)
        var webSource: FeedWebSource = FeedWebSource.WEB_VIEW
) : DatabaseEntity {

    @Id
    override var databaseId: Long = 0

    lateinit var typeEntity: ToOne<FeedTypeEntity>

    lateinit var restrictionEntities: ToMany<FeedRestrictionEntity>
}