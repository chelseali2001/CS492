package com.example.cs492finalproject

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val roomListAdaptor = RoomListAdaptor(::onRoomClick)
    lateinit var galleryFragment: GalleryFragment
    lateinit var searchFragment: SearchFragment
    lateinit var createpostFragment: CreatePostFragment
    lateinit var detailFragment: PostDetailFragment
    lateinit var thumbnail : Bitmap
    private lateinit var roomsListRV: RecyclerView

    var oldColor : String? = ""
    var oldLocation : String? = ""

    //Todo use "RoomListAdaptor for RecyclerView for main page
    // Figure out how to get the room object to have a picture url or asset link?"
    var roomsMain: MutableList<Room> = mutableListOf(
        Room("@drawable/room1", "Sofa", "Room photo 1", "red", "hello"),
        Room("@drawable/room2", "Mirror", "Room photo 2", "hello", "Oregon"),
        Room("@drawable/room3", "Shelf", "Room photo 3", "hello", "hello"),
        Room("@drawable/room4", "tag4", "Room photo 4", "hello", "hello"),
        Room("@drawable/room5", "tag5", "Room photo 5", "hello", "hello")
    )

    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creating recycler view
        roomsListRV = findViewById(R.id.rv_room_list)
        roomsListRV.layoutManager = LinearLayoutManager(this)
        roomsListRV.setHasFixedSize(true)

        roomsListRV.adapter = roomListAdaptor


        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        val color = sharedPrefs.getString(
            getString(R.string.pref_color_key),
            null
        )

        val location = sharedPrefs.getString(
            getString(R.string.pref_location_key),
            null
        )

        val newRooms: MutableList<Room> = mutableListOf()

        for (x in roomsMain) {
            if (x.Color == color.toString() || color.toString() == "") {
                if (x.Location == location.toString() || location.toString() == "") {
                    newRooms.add(x)
                }
            }
        }

        roomListAdaptor.updateRooms(newRooms)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        galleryFragment = GalleryFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, galleryFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        roomsListRV.visibility = View.VISIBLE

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.gallery -> {
                    galleryFragment = GalleryFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, galleryFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()

                    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

                    val color = sharedPrefs.getString(
                        getString(R.string.pref_color_key),
                        null
                    )

                    val location = sharedPrefs.getString(
                        getString(R.string.pref_location_key),
                        null
                    )

                    val newRooms: MutableList<Room> = mutableListOf()

                    for (x in roomsMain) {
                        if (x.Color == color.toString() || color.toString() == "") {
                            if (x.Location == location.toString() || location.toString() == "") {
                                newRooms.add(x)
                            }
                        }
                    }

                    roomListAdaptor.updateRooms(newRooms)
                    roomsListRV.visibility = View.VISIBLE
                    true
                }
                R.id.search -> {
                    searchFragment = SearchFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, searchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                    roomsListRV.visibility = View.GONE
                    true
                }
                R.id.create_post -> {
                    createpostFragment = CreatePostFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, createpostFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                    roomsListRV.visibility = View.GONE
                    true
                }
                else -> false
            }
        }
    }

    private fun onRoomClick(room: Room) {
        roomsListRV.visibility = View.GONE
        detailFragment = PostDetailFragment(room)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, detailFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}