package com.bitc.foodytripapp.Data

data class UserModel(
    var userId : String,
    var password : String,
    var email : String
)

data class UserListModel(
    var users : MutableList<UserModel>
)
