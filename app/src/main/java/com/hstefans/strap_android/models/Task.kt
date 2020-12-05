package com.hstefans.strap_android.models


data class Task(
    var uid: String,
    var title: String,
    var description: String,
    var location: String,
    var doneStatus: Boolean
) {
    constructor() : this("", "", "", "", false)

}