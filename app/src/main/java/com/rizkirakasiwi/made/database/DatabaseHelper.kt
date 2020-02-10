package com.rizkirakasiwi.made.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.rizkirakasiwi.made.data.other.FavoriteDb
import com.rizkirakasiwi.made.data.other.Reminder

class DatabaseHelper (context: Context):SQLiteOpenHelper(context,"FavoriteDb",null,1){

    companion object{
        val TABLE_MOVIE  = "FavotiteMovie"
        val TABLE_TVSHOW = "FavoriteTvShow"
        val TABLE_REMINDER = "Reminder"
    }

    private val tableReminder = "CREATE TABLE $TABLE_REMINDER(" +
            "${Reminder.ID} INTEGER PRIMARY KEY DEFAULT 1," +
            "${Reminder.RELEASE_REMINDER} INTEGER DEFAULT 0," +
            "${Reminder.REMINDER_DAILY} INTEGER DEFAULT 0" +
            ")"


    private val tableMovie = "CREATE TABLE $TABLE_MOVIE(" +
            "${FavoriteDb.ID} TEXT PRIMARY KEY," +
            "${FavoriteDb.BAHASA} TEXT," +
            "${FavoriteDb.DESKRIPSI} TEXT," +
            "${FavoriteDb.IMAGE_PATH} TEXT," +
            "${FavoriteDb.GENRE} TEXT," +
            "${FavoriteDb.JUDUL} TEXT," +
            "${FavoriteDb.RATING} TEXT," +
            "${FavoriteDb.TAHUN} TEXT" +
            ")"

    private val tableTvShow = "CREATE TABLE $TABLE_TVSHOW(" +
            "${FavoriteDb.ID} TEXT PRIMARY KEY," +
            "${FavoriteDb.BAHASA} TEXT," +
            "${FavoriteDb.DESKRIPSI} TEXT," +
            "${FavoriteDb.IMAGE_PATH} TEXT," +
            "${FavoriteDb.GENRE} TEXT," +
            "${FavoriteDb.JUDUL} TEXT," +
            "${FavoriteDb.RATING} TEXT," +
            "${FavoriteDb.TAHUN} TEXT" +
            ")"

    private val TAG = "DatabaseHelper"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(tableMovie)
        db?.execSQL(tableTvShow)
        db?.execSQL(tableReminder)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TVSHOW")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_REMINDER")
    }


    fun insertData(tableName:String, values:ContentValues){
        val db = this.writableDatabase
        val result = db.insert(tableName,null, values)
        if(result == (-1).toLong()){
            Log.i(TAG, "Failed to insert")
        }else{
            Log.i(TAG, "Success to insert")
        }
        db.close()
    }

    fun loadFav(tableName: String) : MutableList<FavoriteDb>{
        val list = mutableListOf<FavoriteDb>()
        val database = this.readableDatabase

        val query = "SELECT * FROM $tableName"
        val result = database.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                val data = FavoriteDb(
                    id = result.getString(result.getColumnIndex(FavoriteDb.ID)),
                    bahasa = result.getString(result.getColumnIndex(FavoriteDb.BAHASA)),
                    deskripsi = result.getString(result.getColumnIndex(FavoriteDb.DESKRIPSI)),
                    genre = result.getString(result.getColumnIndex(FavoriteDb.GENRE)),
                    image_path = result.getString(result.getColumnIndex(FavoriteDb.IMAGE_PATH)),
                    judul = result.getString(result.getColumnIndex(FavoriteDb.JUDUL)),
                    rating = result.getString(result.getColumnIndex(FavoriteDb.RATING)),
                    tahun = result.getString(result.getColumnIndex(FavoriteDb.TAHUN))
                )

                list.add(data)
            }while (result.moveToNext())
        }
        result.close()
        return list
    }


    fun loadReminder() : MutableList<Reminder>{
        val list = mutableListOf<Reminder>()
        val database = this.readableDatabase

        val query = "SELECT * FROM $TABLE_REMINDER"
        val result = database.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                val data = Reminder(
                    id = result.getInt(result.getColumnIndex(Reminder.ID)),
                    reminderDaily = result.getInt(result.getColumnIndex(Reminder.REMINDER_DAILY)),
                    releaseReminder = result.getInt(result.getColumnIndex(Reminder.RELEASE_REMINDER))
                )

                list.add(data)
            }while (result.moveToNext())
        }
        result.close()
        return list
    }

    fun insertReminder(contentValues: ContentValues){
        val db = this.writableDatabase
        val data = loadReminder()
        if(data.isNullOrEmpty()) {
            db.insert(TABLE_REMINDER, null,contentValues)
            db.close()
        }
    }

    fun updateReminder(contentValues: ContentValues){
        val db = this.writableDatabase
        val result = db.update(TABLE_REMINDER, contentValues, "${Reminder.ID}=1",null)
        if(result == -1){
            Log.i(TAG, "Failed to update")
        }else{
            Log.i(TAG, "Success to update")
        }
        db.close()

    }

    fun deleteFav(tableName: String, id:String){
        val db = this.writableDatabase
        val result = db.delete(tableName,"id=$id", null)
        if(result == -1){
            Log.i(TAG, "Failed to delete")
        }else{
            Log.i(TAG, "Success to delete")
        }
        db.close()
    }

}