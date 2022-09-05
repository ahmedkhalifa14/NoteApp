package com.example.noteapp.db

import androidx.room.TypeConverter
import com.example.noteapp.model.AudioRecord
import com.example.noteapp.model.ColorObject
import com.google.gson.Gson
import java.util.*

class Converters {
    @TypeConverter
    fun fromAudio(audioRecord: AudioRecord): String {
        return Gson().toJson(audioRecord)
    }

    @TypeConverter
    fun toAudio(audioRecord:String): AudioRecord {
        return Gson().fromJson(audioRecord,AudioRecord::class.java)
    }
    @TypeConverter
    fun fromData(date:Date): String {
        return Gson().toJson(date)
    }

    @TypeConverter
    fun toDate(date:String): Date {
        return Gson().fromJson(date,Date::class.java)
    }

    @TypeConverter
    fun fromColor(colorObject: ColorObject): String {
        return Gson().toJson(colorObject)
    }

    @TypeConverter
    fun toColor(colorObject: String): ColorObject {
        return Gson().fromJson(colorObject,ColorObject::class.java)
    }
}