package com.example.expertgoggles

class userAudio {

    private var text : String =""
    private var userStatus : String =""
    private var userName : String =""

    fun setText(givenText : String) {
        this.text = givenText
    }


    fun getText(): String? {
        return text
    }

    fun setUserName(userName : String) {
        this.userName = userName
    }

    fun getUserName(): String? {
        return text
    }

    fun setUserStatus(userName: String) {
        this.userStatus = userStatus
    }

    fun setUserStatus(): String {
        return userStatus
    }

}