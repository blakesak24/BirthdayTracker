package com.mistershorr.birthdaytracker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
data class Person(
    var name :String ="",
    var birthday: Date= Date(1646932056741),
    var giftbudget:Double = .99,
    var giftwanted:String = "string",
//    var Giftsgiven: List<String> = listOf(),
//    var Giftsrecieved: List<String> = listOf(),
    var giftPurchased: Boolean = false,
    var ownerId : String? = null,
    var objectId : String? = null
): Parcelable {

}
