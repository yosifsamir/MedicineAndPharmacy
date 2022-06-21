package com.example.toothpaste.model

import com.example.toothpaste.utils.Constants.CONSTANT_PROFILE_PIC_URL

data class User(
    val uid: String? = "",
    val email: String? = "",
    val username: String?  = "",
    val regNo: String? = "",
    val userPhoneNum: String? = "",
    val profilePictureUrl: String? = CONSTANT_PROFILE_PIC_URL,
    val privilege: String? = "User"
)
