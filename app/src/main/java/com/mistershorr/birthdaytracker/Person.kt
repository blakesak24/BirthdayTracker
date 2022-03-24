package com.mistershorr.birthdaytracker

import java.util.*

data class Person(
    var name :String ="",
    var birthday: Date= Date(1646932056741),
    var giftbudget:Double = .99,
    var giftwanted:String = "string",
    var Giftsgiven: List<String> = listOf(),
    var Giftsrecieved: List<String> = listOf(),
    var giftPurchased: Boolean = false,
    var ownerId : String? = null,
    var objectid : String? = null
)
