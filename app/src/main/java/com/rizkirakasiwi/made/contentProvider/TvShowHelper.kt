package com.rizkirakasiwi.made.contentProvider

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.rizkirakasiwi.made.data.other.FavoriteDb
import com.rizkirakasiwi.made.database.DatabaseHelper
import com.rizkirakasiwi.made.database.DatabaseHelper.Companion.TABLE_TVSHOW
import java.sql.SQLException

class TvShowHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_TVSHOW
        private var INSTANCE: TvShowHelper? = null

        fun getInstance(context: Context): TvShowHelper =
            INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: TvShowHelper(
                        context
                    )
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            FavoriteDb.ID,
            null)
    }

    fun queryById(id: String): Cursor {
        return database.query(DATABASE_TABLE, null, "${FavoriteDb.ID} = ?", arrayOf(id), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${FavoriteDb.ID} = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${FavoriteDb.ID} = '$id'", null)
    }
}