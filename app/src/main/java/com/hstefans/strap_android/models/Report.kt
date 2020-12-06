package com.hstefans.strap_android.models


data class Report(
    var uid: String,
    var location: String,
    var damage: String,
    var date: String,
    var photoRef: String

) {
    constructor() : this("", "", "", "", "")

}