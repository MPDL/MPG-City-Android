package com.mpdl.mpgcitymap.mvvm.repository.entity

class CustomItem {
    var type :Int//0 webView /1 dialog
    var url = ""
    var email = ""
    var telephone = ""
    var x:Int
    var y:Int
    var imgRes:Int
    var viewId:Int

    constructor(viewId: Int, imgRes: Int, x: Int, y: Int,url: String){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = 0
        this.url = url
    }

    constructor(viewId: Int, imgRes: Int, x: Int, y: Int,type:Int,assetName: String){
        this.viewId = viewId
        this.imgRes = imgRes
        this.x = x
        this.y = y
        this.type = type
        this.url = "file:///android_asset/$assetName"
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