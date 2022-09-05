package com.example.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp.model.Note


@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
companion object {
    val DATABASE_NAME: String = "note_db"
}

//    companion object {
//        @Volatile
//        private var noteInstance: NoteDatabase? = null
//        private val LOCK = Any()
//        operator fun invoke(context: Context) = noteInstance ?: synchronized(LOCK) {
//            noteInstance ?: createDatabase(context).also { noteInstance = it }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                NoteDatabase::class.java,
//                "note"
//            ).build()
//    }
}