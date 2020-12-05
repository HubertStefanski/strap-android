package com.hstefans.strap_android.models

import java.io.Serializable

class Task : Serializable {
    public var uid: String = ""
    public var title: String = ""
    public var assignee: String = ""
    public var description: String = ""
    public var location: String = ""
    public var doneStatus: Boolean = false


    constructor() {}

    constructor(
        uid: String,
        title: String,
        assignee: String,
        description: String,
        location: String,
        doneStatus: Boolean
    )
}