package com.example.noteapp.util

import com.example.noteapp.model.ColorObject

class ColorList
{
    private val blackHex = "000000"
    private val whiteHex = "FFFFFF"

    val defaultColor: ColorObject = basicColors()[0]

    fun colorPosition(colorObject: ColorObject): Int
    {
        for (i in basicColors().indices)
        {
            if(colorObject == basicColors()[i])
                return i
        }
        return 0
    }

    fun basicColors(): List<ColorObject>
    {
        return listOf(
            ColorObject("Black", blackHex, whiteHex),
            ColorObject("Silver", "C0C0C0", blackHex),
            ColorObject("Gray", "808080", whiteHex),
            ColorObject("Maroon", "800000", whiteHex),
            ColorObject("Red", "FF0000", whiteHex),
            ColorObject("Fuchsia", "FF00FF", whiteHex),
            ColorObject("Green", "008000", whiteHex),
            ColorObject("Lime", "00FF00", blackHex),
            ColorObject("Olive", "808000", whiteHex),
            ColorObject("Yellow", "FFFF00", blackHex),
            ColorObject("Navy", "000080", whiteHex),
            ColorObject("Blue", "0000FF", whiteHex),
            ColorObject("Teal", "008080", whiteHex),
            ColorObject("Aqua", "00FFFF", blackHex),
            ColorObject("IndianRed", "CD5C5C", whiteHex),
            ColorObject("Salmon", "FA8072", whiteHex),
            ColorObject("LightSalmon", "FFA07A", whiteHex),
            ColorObject("DarkSalmon", "E9967A", whiteHex),
            ColorObject("Jade", "00A36C", whiteHex),
            ColorObject("Sky Blue", "87CEEB", whiteHex),
            ColorObject("Bright Yellow", "FFEA00", blackHex),
            ColorObject("Canary Yellow", "FFFF8F", blackHex),
            ColorObject("Yellow Orange", "FFAA33", whiteHex),
            ColorObject("Saffron", "F4C430", whiteHex),
            ColorObject("Crimson", "DC143C", whiteHex),
            ColorObject("Byzantium", "702963", whiteHex),
            ColorObject("Blood Red", "880808", whiteHex),




        )
    }
}