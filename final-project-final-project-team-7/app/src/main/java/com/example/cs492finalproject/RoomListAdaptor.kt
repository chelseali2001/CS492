package com.example.cs492finalproject

import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

//main adapter for the recycler view that will hold room objects on the main page
//TODO put this as passed arg private val onClick: (Room) -> Unit
class RoomListAdaptor(private val onClick: (Room) -> Unit) : RecyclerView.Adapter<RoomListAdaptor.ViewHolder> () {
    var rooms = mutableListOf<Room>()

    fun updateRooms(newRooms: MutableList<Room>?){
        rooms = newRooms?: mutableListOf()
        notifyDataSetChanged()
    }

    //number of room objects lsited
    override fun getItemCount() = this.rooms.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO: Crete proper layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_list_item, parent, false)
        //TODO add onCLick as arg , onClick
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.rooms[position])
    }



    //embedded subclass viewholder
//TODO add this to passed args , val onClick: (Room) -> Unit
    class ViewHolder(itemView: View, private val onClick: (Room) -> Unit) : RecyclerView.ViewHolder(itemView) {
        //declare view holders for photo and caption element of Room here
        private val imageIV: ImageView = itemView.findViewById(R.id.ImageTest)

        private var currentRoom: Room? = null

        //iniitalizing Room onClick listener
        init {
            itemView.setOnClickListener{
                currentRoom?.let(onClick)
            }
        }

        fun bind(room: Room) {
            currentRoom = room

            val ctx = itemView.context
            //rest of bind func
            val drawableId: Int = ctx.getResources().getIdentifier(currentRoom!!.Image, null, ctx!!.packageName)
            val res: Drawable = ctx.getResources().getDrawable(drawableId)
            imageIV.setImageDrawable(res)
        }
    }

}