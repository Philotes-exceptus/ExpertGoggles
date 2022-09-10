package com.example.expertgoggles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


@Suppress("NAME_SHADOWING")
class UserListAdapter(context: Context?, countryList: ArrayList<Users>?) :
    ArrayAdapter<Users?>(context!!, 0, countryList!! as List<Users?>) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView!!, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView!!, parent)
    }

    private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertview = convertView
        if (convertView == null) {
            convertview = LayoutInflater.from(context).inflate(
                R.layout.user_add, parent, false
            )
        }
        val imageViewFlag = convertView.findViewById<ImageView>(R.id.image_view_flag)
        val textViewName = convertView.findViewById<TextView>(R.id.text_view_name)
        val currentItem: Users? = getItem(position)
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getImage())
            textViewName.setText(currentItem.getName())
        }
        return convertview
    }
}