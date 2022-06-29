package com.esan2022.pc02.models

import com.google.firebase.Timestamp

data class UserModel(
    var dni: String? = null,
    var fullname: String? = null,
    var password: String? =  null
)