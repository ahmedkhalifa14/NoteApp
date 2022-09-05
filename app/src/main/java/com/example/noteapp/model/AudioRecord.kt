package com.example.noteapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AudioRecord(
    val audioId:Int,
    val fileName:String,
    val filePath:String,
    val timestamp: Long,
    val duration: String,
    val ampsPath:String
        ):Parcelable