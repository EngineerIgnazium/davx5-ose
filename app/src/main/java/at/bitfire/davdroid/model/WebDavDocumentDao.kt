package at.bitfire.davdroid.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WebDavDocumentDao: SyncableDao<WebDavDocument> {

    @Query("SELECT * FROM webdav_document WHERE id=:id")
    fun get(id: Long): WebDavDocument?

    @Query("SELECT * FROM webdav_document WHERE mountId=:mountId AND (parentId=:parentId OR (parentId IS NULL AND :parentId IS NULL)) AND name=:name")
    fun getByParentAndName(mountId: Long, parentId: Long?, name: String): WebDavDocument?

    @Query("SELECT * FROM webdav_document WHERE parentId=:parentId")
    fun getChildren(parentId: Long): List<WebDavDocument>

    @Query("SELECT * FROM webdav_document WHERE parentId IS NULL")
    fun getRootsLive(): LiveData<List<WebDavDocument>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(document: WebDavDocument): Long

    @Query("DELETE FROM webdav_document WHERE parentId=:parentId")
    fun removeChildren(parentId: Long)


    // complex operations

    @Transaction
    fun getOrCreateRoot(mountId: Long, displayName: String): WebDavDocument {
        getByParentAndName(mountId, null, "")?.let { existing ->
            return existing
        }

        val newDoc = WebDavDocument(
            mountId = mountId,
            parentId = null,
            name = "",
            isDirectory = true,
            displayName = displayName
        )
        val id = insertOrReplace(newDoc)
        newDoc.id = id
        return newDoc
    }

}