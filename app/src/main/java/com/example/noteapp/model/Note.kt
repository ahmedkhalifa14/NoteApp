package com.example.noteapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey
    var id: String="",
    val noteTitle: String="",
    val noteBody: String="",
    var user_id: String = "",
    @ServerTimestamp
    val date: Date = Date(),
    val colorObject:ColorObject?=null
   // val audioRecord: AudioRecord?
) : Parcelable
