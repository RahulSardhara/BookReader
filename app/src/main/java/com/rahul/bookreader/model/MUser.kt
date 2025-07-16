package com.rahul.bookreader.model

data class MUser(
    val id: String?,
    val userId: String?,
    val displayName: String?,
    val avatarUrl: String?,
    val quote: String?,
    val profession: String?
){
    fun toMap(): MutableMap<String, Any>{
        val map = mutableMapOf<String, Any>()
        id?.let { map["id"] = it }
        userId?.let { map["user_id"] = it }
        displayName?.let { map["display_name"] = it }
        avatarUrl?.let { map["avatar_url"] = it }
        quote?.let { map["quote"] = it }
        profession?.let { map["profession"] = it }
        return map
    }
}
