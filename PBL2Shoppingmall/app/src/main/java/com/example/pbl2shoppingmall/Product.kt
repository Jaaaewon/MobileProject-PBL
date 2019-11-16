package com.example.pbl2shoppingmall

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var category:String,
                   var name:String,
                   var path:String,
                   var price:String,
                   var context:String): Parcelable {
}