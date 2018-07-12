package com.chrynan.launcher.database.converter

import com.chrynan.launcher.model.FeedWebSource
import io.objectbox.converter.PropertyConverter

class FeedWebSourceConverter : PropertyConverter<FeedWebSource, Int> {

    override fun convertToDatabaseValue(entityProperty: FeedWebSource?) =
            if (entityProperty == FeedWebSource.CHROME_CUSTOM_TABS) FeedWebSource.TYPE_ID_CHROME_CUSTOM_TABS else FeedWebSource.TYPE_ID_WEB_VIEW

    override fun convertToEntityProperty(databaseValue: Int?) =
            if (databaseValue == FeedWebSource.TYPE_ID_CHROME_CUSTOM_TABS) FeedWebSource.CHROME_CUSTOM_TABS else FeedWebSource.WEB_VIEW
}