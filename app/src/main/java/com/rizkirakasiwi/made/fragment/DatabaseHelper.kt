package com.rizkirakasiwi.made.fragment

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.rizkirakasiwi.made.fragment.data.FavoriteDb
import com.rizkirakasiwi.made.fragment.data.movie.MovieResult
import com.rizkirakasiwi.made.fragment.data.tvShow.TvResult

class DatabaseHelper (context: Context):SQLiteOpenHelper(context,"FavoriteDb",null,1){

    companion object{
        val TABLE_MOVIE  = "FavotiteMovie"
        val TABLE_TVSHOW = "FavoriteTvShow"
    }
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

    private val tableTvShow = "CREATE TABLE ${TABLE_TVSHOW}(" +
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
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_MOVIE}")
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_TVSHOW}")
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
                    result.getString(result.getColumnIndex(FavoriteDb.ID)),
                    result.getString(result.getColumnIndex(FavoriteDb.BAHASA)),
                    result.getString(result.getColumnIndex(FavoriteDb.DESKRIPSI)),
                    result.getString(result.getColumnIndex(FavoriteDb.GENRE)),
                    result.getString(result.getColumnIndex(FavoriteDb.IMAGE_PATH)),
                    result.getString(result.getColumnIndex(FavoriteDb.JUDUL)),
                    result.getString(result.getColumnIndex(FavoriteDb.RATING)),
                    result.getString(result.getColumnIndex(FavoriteDb.TAHUN))
                )

                list.add(data)
            }while (result.moveToNext())
        }
        result.close()
        return list
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