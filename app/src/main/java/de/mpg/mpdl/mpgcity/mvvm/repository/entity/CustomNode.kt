package de.mpg.mpdl.mpgcity.mvvm.repository.entity

import com.google.ar.sceneform.AnchorNode

class CustomNode {
    var node: AnchorNode
    var type :Int
    var url = ""
    var email = ""
    var telephone = ""
    var x:Int
    var y:Int
    var imgRes:Int

    constructor(name: String, imgRes: Int, x: Int, y: Int, type: Int, url: String){
        this.node = AnchorNode()
        this.node.name = name
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = type
        this.url = url
    }

    constructor(name: String, imgRes: Int, x: Int, y: Int, type: Int, email: String, telephone: String){
        node = AnchorNode()
        node.name = name
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = type
        this.email = email
        this.telephone = telephone
    }
}