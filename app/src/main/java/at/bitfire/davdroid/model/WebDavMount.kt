package at.bitfire.davdroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Entity(tableName = "webdav_mount")
data class WebDavMount(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,

    /** display name of the WebDAV mount */
    var name: String,

    /** URL of the WebDAV service, including trailing slash */
    var url: HttpUrl

    // credentials are stored using CredentialsStore

): IdEntity