package com.example.expertgoggles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter(var context: Context, var flags: IntArray, var countryNames: Array<String>) :
    BaseAdapter() {
    var inflter: LayoutInflater
    override fun getCount(): Int {
        return flags.size
    }

    override fun getItem(i: Int): Any {
        return 0
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        if(view!=null)
        {
        view = inflter.inflate(R.layout.user_add, null)
        val icon = view.findViewById<View>(R.id.imageView) as ImageView
        val names = view.findViewById<View>(R.id.textView) as TextView
        icon.setImageResource(flags[i])
        names.text = countryNames[i]
    }
        return view
    }

    init {
        inflter = LayoutInflater.from(context)
    }
}