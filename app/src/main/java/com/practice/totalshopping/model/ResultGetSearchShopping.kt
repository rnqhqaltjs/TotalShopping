package com.practice.totalshopping.model

data class ResultGetSearchShopping(
    var lastBuildDate: String = "",
    var total: Int = 0,
    var start: Int = 0,
    var display: Int = 0,
    var items: List<Items>
)

data class Items(
    var title: String = "",
    var link: String = "",
    var image: String = "",
    var lprice: Int = 0,
//    var hprice: Int = 0,
    var mallName: String ="",
//    var productId: Int = 0,
//    var productType: Int = 0,
    var maker : String = "",
    var brand : String = "",
    var category1 : String = "",
    var category2 : String = "",
    var category3 : String = ""

)