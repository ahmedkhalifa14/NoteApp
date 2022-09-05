package com.example.noteapp.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
data class ColorObject(
    var name: String="",
    var hex: String="",
    var contrastHex: String=""
) : Parcelable {
    val hexHash: String = "#$hex"
    val contrastHexHash: String = "#$contrastHex"
}