package com.example.expertgoggles

class userAudio {

    private var text : String? =""
    private var userStatus : String? =""
    private var userName : String? =""

    fun setText(givenText : String?) {
        this.text = givenText
    }


    fun getText(): String? {
        return this.text
    }

    fun setUserName(userName : String?) {
        this.userName = userName
    }

    fun getUserName(): String? {
        return this.userName
    }

    fun setUserStatus(userStatus: String?) {
        this.userStatus = userStatus
    }

    fun getUserStatus(): String? {
        return this.userStatus
    }

}