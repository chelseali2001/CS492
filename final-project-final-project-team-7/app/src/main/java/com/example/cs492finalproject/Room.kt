package com.example.cs492finalproject

import android.widget.ImageView
import java.io.Serializable

//image needs to be a room photo? just a place holder String rn
data class Room(
    val Image : String,
    val Tags : String,
    val Description : String,
    val Color : String,
    val Location : String
) : Serializable
