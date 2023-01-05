package com.inxtes.nowplayer.ui.adapter

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.R
import com.inxtes.nowplayer.bean.Music
import com.inxtes.nowplayer.ui.activity.MainActivity
import com.inxtes.nowplayer.utils.FileUtil

class MusicAdapter(private val context:MainActivity) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private val TAG = this::class.simpleName
    private var dataList:ArrayList<Music> = ArrayList()

    init {
        //初始化音乐列表
        dataList = FileUtil.getMusicInMediaStore(App.context)
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val musicImage:ImageView = view.findViewById<ImageView>(R.id.music_image)
        val musicTitle:TextView = view.findViewById<TextView>(R.id.music_title)
        val musicArtist:TextView = view.findViewById<TextView>(R.id.music_artist)
        val musicSize:TextView = view.findViewById<TextView>(R.id.music_size)
        val musicItem:LinearLayout = view.findViewById(R.id.music_item)
        val musicCard:com.google.android.material.card.MaterialCardView = view.findViewById(R.id.music_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout =LayoutInflater.from(parent.context).inflate(R.layout.item_music_layout,parent,false)
        val viewHolder = ViewHolder(itemLayout)
        viewHolder.musicItem.setOnClickListener {
            context.viewPager.currentItem = 1
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.musicTitle.text = dataList[position].title
        holder.musicArtist.text = dataList[position].artist
        holder.musicSize.text = dataList[position].length.toString()

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addMusic(music:Music){
        dataList.add(music)
        notifyItemInserted(dataList.size-1)
    }

}