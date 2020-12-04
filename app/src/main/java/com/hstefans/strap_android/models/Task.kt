package com.hstefans.strap_android.models

data class Task(
    var uid: String,
    var title: String,
    var location: String,
    var description: String,
    var assignee: String,
    var doneStatus: Boolean
) {


}