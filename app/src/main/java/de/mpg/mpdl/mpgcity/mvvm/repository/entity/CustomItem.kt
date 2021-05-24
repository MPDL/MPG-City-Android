package de.mpg.mpdl.mpgcity.mvvm.repository.entity

class CustomItem {
    var type :Int//0 webView /1 webDialog/ 2 imageDialog
    var url = ""
    var email = ""
    var telephone = ""
    var x:Int
    var y:Int
    var imgRes:Int
    var viewId:Int
    var contentRatio:Double = 1.0

    constructor(viewId: Int, imgRes: Int, x: Int, y: Int,url: String){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = 0
        this.url = url
    }

    constructor(viewId: Int, imgRes: Int, x: Int, y: Int,assetName: String,ratio:Double){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = 2
        this.url = "file:///android_asset/$assetName"
        this.contentRatio = ratio
    }

    constructor(viewId: Int, imgRes: Int, x: Int, y: Int,type:Int,url: String,ratio:Double){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = type
        this.url = url
        this.contentRatio = ratio
    }

    constructor(viewId: Int, imgRes: Int, x: Int, y: Int,type:Int,url: String){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = type
        this.url = url
    }


    constructor(viewId: Int, imgRes: Int, x: Int, y: Int, email: String, telephone: String){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = 1
        this.email = email
        this.telephone = telephone
    }
}