package com.example.fooddelivery.Domain
import java.io.Serializable

class CategoryDomain:Serializable {
    var title: String
    var pic: String

    constructor(title: String, pic: String) {
        this.title = title
        this.pic = pic
    }


}
