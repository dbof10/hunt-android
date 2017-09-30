package com.ctech.eaty.request


data class UpdateUserRequest(val user: User) {

    data class User(val email: String,
                    val name: String,
                    val headline: String)

}