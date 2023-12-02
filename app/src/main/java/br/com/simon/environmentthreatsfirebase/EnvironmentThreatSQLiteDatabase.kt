package br.com.simon.environmentthreatsfirebase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import java.util.Optional

const val TABLE_NAME: String = "environment_threats"
const val COLUMN_ADDRESS: String = "address"
const val COLUMN_DATE: String = "date"
const val COLUMN_DESCRIPTION: String = "description"

class EnvironmentThreatSQLiteDatabase(context: Context) {

    private val db: SQLiteDatabase

    init {
        db = EnvironmentThreatSQLiteDatabaseHelper(context).writableDatabase
    }

    fun addEnvironmentThreat(environmentThreat: EnvironmentThreat): Long {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ADDRESS, environmentThreat.address)
        contentValues.put(COLUMN_DATE, environmentThreat.date)
        contentValues.put(COLUMN_DESCRIPTION, environmentThreat.description)
        return db.insert(TABLE_NAME, null, contentValues)
    }

    @SuppressLint("Range")
    fun getEnvironmentThreat(id: Long): Optional<EnvironmentThreat> {
        val columns = arrayOf(
            _ID,
            COLUMN_ADDRESS,
            COLUMN_DATE,
            COLUMN_DESCRIPTION
        )
        val args = arrayOf(
            id.toString()
        )

        val cursor: Cursor = db.query(TABLE_NAME, columns, "$_ID=?", args, null, null, _ID)

        if (cursor.count != 1) {
            return Optional.empty()
        }

        cursor.moveToNext()

        val threatId = cursor.getLong(cursor.getColumnIndex(_ID))
        val address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
        val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
        val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))

        cursor.close()

        return Optional.of(EnvironmentThreat(threatId, address, date, description))
    }

    @SuppressLint("Range")
    fun getEnvironmentThreats(): List<EnvironmentThreat> {
        val columns = arrayOf(
            _ID,
            COLUMN_ADDRESS,
            COLUMN_DATE,
            COLUMN_DESCRIPTION
        )

        val cursor: Cursor = db.query(TABLE_NAME, columns, null, null, null, null, _ID)

        val environmentThreats: MutableList<EnvironmentThreat> = ArrayList()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(_ID))
            val address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
            val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
            environmentThreats.add(EnvironmentThreat(id, address, date, description))
        }

        cursor.close()

        return environmentThreats
    }

    fun removeEnvironmentThreat(environmentThreatId: Int): Int {
        val args = arrayOf(
            environmentThreatId.toString()
        )
        return db.delete(TABLE_NAME, "$_ID=?", args)
    }

    fun updateEnvironmentThreat(environmentThreat: EnvironmentThreat): Int {
        val args = arrayOf(
            environmentThreat.id.toString()
        )
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ADDRESS, environmentThreat.address)
        contentValues.put(COLUMN_DATE, environmentThreat.date)
        contentValues.put(COLUMN_DESCRIPTION, environmentThreat.description)
        return db.update(TABLE_NAME, contentValues, "$_ID=?", args)
    }

}

class EnvironmentThreatSQLiteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($_ID INTEGER PRIMARY KEY, $COLUMN_ADDRESS TEXT, $COLUMN_DATE TEXT, $COLUMN_DESCRIPTION TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "threats.db"
        private const val DATABASE_VERSION = 1
    }

}



