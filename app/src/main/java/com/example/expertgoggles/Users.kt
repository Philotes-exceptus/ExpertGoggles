package com.example.expertgoggles

class Users {

    private var UserName: String? = null
    private var UserImage = 0

    fun userItem(Name: String?, Image: Int) {
        UserName = Name
        UserImage = Image
    }

    fun getName(): String? {
        return UserName
    }

    fun getImage(): Int {
        return UserImage
    }

}