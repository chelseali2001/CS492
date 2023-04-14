package com.example.cs492finalproject

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class PostDetailFragment(room: Room) : Fragment(R.layout.fragment_detail) {

    private lateinit var descriptionTV: TextView
    private lateinit var tagsTV: TextView
    private lateinit var locationTV: TextView

    //this will need to be the imageview

    private val roomDetail: Room = room



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var picture: ImageView = requireView().findViewById(R.id.detail_picture)
        val ctx = requireContext()
        val drawable: Int = ctx.resources.getIdentifier(roomDetail.Image, null,ctx.packageName)
        val res: Drawable = ctx.resources.getDrawable(drawable)
        picture.setImageDrawable(res)

        descriptionTV = view.findViewById(R.id.detail_desc)
        tagsTV = view.findViewById(R.id.detail_tags)
        locationTV = view.findViewById(R.id.detail_location)

        descriptionTV.text = "Description: " + roomDetail.Description
        tagsTV.text = "Tags: " + roomDetail.Tags
        locationTV.text = "Location: " + roomDetail.Location


    }
}