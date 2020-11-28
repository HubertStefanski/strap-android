package org.hstefans.strap.app.models



class Report(
        var uid: String,
        var location: String,
        var description: String,
        var damage: String,
        var resolution: String,
        var reportee: String
) {

    val uidProperty = uid

    val locationProperty = location

    val descriptionProperty = description

    val damageProperty = damage

    val resolutionProperty = resolution

    val reporteeProperty = description


}