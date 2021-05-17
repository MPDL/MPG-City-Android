package com.mpdl.mpgcitymap.mvvm.ui.fragment

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.fyt.mvvm.base.BaseFragment
import com.google.ar.core.AugmentedImage
import com.google.ar.core.Pose
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.DpToMetersViewSizer
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.repository.entity.CustomItem
import com.mpdl.mpgcitymap.mvvm.ui.activity.MainActivity
import com.mpdl.mpgcitymap.mvvm.ui.widget.ImageDialog
import com.mpdl.mpgcitymap.mvvm.ui.widget.WebDialog
import com.mpdl.mpgcitymap.mvvm.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber
import java.util.function.Consumer
import kotlin.math.abs

class MainFragment : BaseFragment<MainViewModel>(), Scene.OnUpdateListener, View.OnClickListener {
    override fun initViewModel(): MainViewModel = getViewModel()
    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View = inflater.inflate(R.layout.fragment_main,container,false)

    private var arFragment: ArFragment? = null
    private var showNode:Boolean = true
    private var dpPerMeters = 0

    private val infoNodeHeight = 260
    private var mInfoView: View? = null
    private var mListCityNode: AnchorNode? = null
    private var listCityView: ConstraintLayout? = null
    private lateinit var cityItemList: MutableList<CustomItem>
    private val imageViewWidth = 300
    private lateinit var curPose:Pose
    private lateinit var curQuaternion:Quaternion

    private var webDialog: WebDialog? = null
    private var imageDialog: ImageDialog? = null


    override fun initData(savedInstanceState: Bundle?) {
        arFragment = childFragmentManager.findFragmentById(R.id.sceneform_fragment) as CustomArFragment
        arFragment?.planeDiscoveryController?.hide()
        arFragment?.arSceneView?.scene?.addOnUpdateListener(this)

        initListCity()
    }


    private fun initListCity(){
        listCityView = View.inflate(requireContext(),R.layout.list_city,null) as ConstraintLayout?

        cityItemList = mutableListOf()
        cityItemList.add(CustomItem(R.id.item0,R.mipmap.city_map_0,-1,0,3,"https://www.youtube.com/watch?v=zZFyD8kSaIo"))
        cityItemList.add(CustomItem(R.id.item1,R.mipmap.city_map_1,-1,1,"https://www.mpg.de/biography-martin-stratmann"))
        cityItemList.add(CustomItem(R.id.item2,R.mipmap.city_map_2,1,1,"https://www.mpg.de/adminhq"))
        cityItemList.add(CustomItem(R.id.item3,R.mipmap.city_map_3,1,0,"https://www.mpdl.mpg.de/en"))
        cityItemList.add(CustomItem(R.id.item3_1,R.mipmap.city_map_3_1,2,0,"https://mpdl-services.de/"))
        cityItemList.add(CustomItem(R.id.item4,R.mipmap.city_map_4,1,-1,"http://docs.google.com/gview?url=https://www.mpg.de/197521/statutesMPS.pdf"))
        cityItemList.add(CustomItem(R.id.item5,R.mipmap.city_map_5,-1,-1,"https://www.archiv-berlin.mpg.de/2523/en"))
        cityItemList.add(CustomItem(R.id.item6,R.mipmap.city_map_6,-2,0,2,"item_6_content.jpg"))
        cityItemList.add(CustomItem(R.id.item7,R.mipmap.city_map_7,-3,1,"https://www.mpg.de/about-us/governing-bodies"))
        cityItemList.add(CustomItem(R.id.item8,R.mipmap.city_map_8,-2,2,"https://www.mpg.de/about-us/governing-bodies"))
        cityItemList.add(CustomItem(R.id.item9,R.mipmap.city_map_9,0,2,3,"https://www.youtube.com/watch?v=X2yDs5FHDok&feature=youtu.be"))
        cityItemList.add(CustomItem(R.id.item10,R.mipmap.city_map_10,2,2,"https://mpgcityweek.mpdl.mpg.de/advisory-committee-for-computing-facilities/"))

        cityItemList.add(CustomItem(R.id.item11,R.mipmap.city_map_11,3,1,"https://leadnet.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item12,R.mipmap.city_map_12,4,0,"https://groupleaders.mpdl.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item13,R.mipmap.city_map_13,3,-1,"https://oambassadors.mpdl.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item14,R.mipmap.city_map_14,2,-2,"https://mpgcityweek.mpdl.mpg.de/gemeinsamenetzwerkzentrum-joint-network-centre/"))
        cityItemList.add(CustomItem(R.id.item15,R.mipmap.city_map_15,0,-2,"https://www.mpcdf.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item16,R.mipmap.city_map_16,-2,-2,"http://www.postdocnet.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item17,R.mipmap.city_map_17,-3,-1,"https://www.nachhaltigkeitsnetzwerk.mpg.de/#"))
        cityItemList.add(CustomItem(R.id.item18,R.mipmap.city_map_18,-4,0,"https://www.gwdg.de/"))
        cityItemList.add(CustomItem(R.id.item19,R.mipmap.city_map_19,-5,1,"https://www.mpg.de/berlin_office"))
        cityItemList.add(CustomItem(R.id.item20,R.mipmap.city_map_20,-1,3,"https://digitalchange.mpdl.mpg.de/"))

        cityItemList.add(CustomItem(R.id.item21,R.mipmap.city_map_21,1,3,"https://mpgc.mpipz.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item22,R.mipmap.city_map_22,5,1,"https://www.mpg.de/office-brussels"))
        cityItemList.add(CustomItem(R.id.item23,R.mipmap.city_map_23,6,0,"https://www.mpg.de/186712/conference-venues"))
        cityItemList.add(CustomItem(R.id.item24,R.mipmap.city_map_24,5,-1,"https://www.max-planck-innovation.de/"))
        cityItemList.add(CustomItem(R.id.item25,R.mipmap.city_map_25,1,-3,"https://www.hll.mpg.de/"))
        cityItemList.add(CustomItem(R.id.item26,R.mipmap.city_map_26,-1,-3,"https://mpgcityweek.mpdl.mpg.de/mpg-queer/"))
        cityItemList.add(CustomItem(R.id.item27,R.mipmap.city_map_27,-5,-1,"https://www.dkrz.de/dkrz-partner-for-climate-research?set_language=en&cl=en"))
        cityItemList.add(CustomItem(R.id.item28,R.mipmap.city_map_28,-6,0,"https://mpgcityweek.mpdl.mpg.de/mpg-city-service/"))


        var lengthX: Int
        var lengthY: Int
        for (item in cityItemList){
            val imageView = ImageView(requireContext())
            imageView.setBackgroundResource(R.drawable.bg_item)
            imageView.setOnClickListener(this)
            imageView.id = item.viewId
            imageView.setImageResource(item.imgRes)
            val layoutParams = ConstraintLayout.LayoutParams(imageViewWidth,imageViewWidth)

            lengthX = getLengthX(item.x)
            lengthY = getLengthY(item.y)
            when {
                item.x == 0 -> {
                    layoutParams.leftToLeft = R.id.center
                    layoutParams.rightToRight = R.id.center
                }
                item.x > 0 -> {
                    layoutParams.leftToRight = R.id.center
                    layoutParams.leftMargin = lengthX
                }
                else -> {
                    layoutParams.rightToLeft = R.id.center
                    layoutParams.rightMargin = lengthX
                }
            }

            when {
                item.y == 0 -> {
                    layoutParams.topToTop = R.id.center
                    layoutParams.bottomToBottom = R.id.center
                }
                item.y > 0 -> {
                    layoutParams.bottomToTop = R.id.center
                    layoutParams.bottomMargin = lengthY
                }
                else -> {
                    layoutParams.topToBottom = R.id.center
                    layoutParams.topMargin = lengthY
                }
            }
            imageView.layoutParams = layoutParams
            listCityView?.addView(imageView)
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        webDialog?.let {
            if (it.isShowing){
                it.show()
            }
        }
        imageDialog?.let {
            if (it.isShowing){
                it.show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onUpdate(frameTime: FrameTime?) {
        var frame = arFragment?.arSceneView?.arFrame
        for (augmentedImage in frame?.getUpdatedTrackables(AugmentedImage::class.java)!!){
            if (MainActivity.SCAN_IMAGE_NAMES.contains(augmentedImage.name)){
                Timber.d( "augmentedImage trackingState: "+augmentedImage.trackingState)
                Timber.d("augmentedImage tx: %s ty: %s tz: %s extentX: %f",augmentedImage.centerPose.tx().toString(),augmentedImage.centerPose.ty().toString(),augmentedImage.centerPose.tz().toString(),augmentedImage.extentX)

                if (augmentedImage.trackingState == TrackingState.TRACKING){
                    Timber.d( "augmentedImage trackingMethod: ${augmentedImage.trackingMethod}")
                    showNode = false
//                    Toast.makeText(requireContext(), "识别到图片", Toast.LENGTH_LONG).show()
                    dpPerMeters = (200/augmentedImage.extentX).toInt()
                    Timber.d( "augmentedImage DpToMetersViewSizer:$dpPerMeters")
//                    showNode(augmentedImage)
                    showListCity(augmentedImage)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showListCity(augmentedImage:AugmentedImage){
        curPose = augmentedImage.centerPose
        var poseTx = curPose.tx()
        var poseTy = curPose.ty()
        var poseTz = curPose.tz()

        curQuaternion = Quaternion(augmentedImage.centerPose.rotationQuaternion[0],
                augmentedImage.centerPose.rotationQuaternion[1],
                augmentedImage.centerPose.rotationQuaternion[2],
                augmentedImage.centerPose.rotationQuaternion[3])
        Timber.d("augmentedImage rotation: %s", curQuaternion.toString())
        val inverse = Quaternion(Vector3(1.0f, 0f, 0f), 270.0f)
        curQuaternion = Quaternion.multiply(curQuaternion, inverse)
        Timber.d("augmentedImage worldRotation: $curQuaternion")

        if (mListCityNode == null){
            mListCityNode = AnchorNode()
            listCityView?.layoutParams = ViewGroup.LayoutParams(5000,5000)
            ViewRenderable
                    .builder()
                    .setView(requireContext(), listCityView)
                    .setSizer(DpToMetersViewSizer(dpPerMeters))
                    .setVerticalAlignment(ViewRenderable.VerticalAlignment.CENTER)
                    .build()
                    .thenAccept(Consumer { renderable: ViewRenderable ->
                        mListCityNode?.renderable = renderable
                        mListCityNode?.worldRotation = curQuaternion
                        mListCityNode?.worldPosition = Vector3(poseTx,poseTy,poseTz)
                        mListCityNode?.setParent(arFragment!!.arSceneView.scene)

                    })
        }
        mListCityNode?.worldRotation = curQuaternion
        mListCityNode?.worldPosition = Vector3(poseTx,poseTy,poseTz)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showNodeInfo(item: CustomItem){
        if (mInfoView != null){
            listCityView?.removeView(mInfoView)
            mInfoView = null
        }

        mInfoView = View.inflate(requireContext(),R.layout.node_info,null)
        mInfoView?.findViewById<TextView>(R.id.tv_close)?.setOnClickListener { _ ->
            listCityView?.removeView(mInfoView)
            mInfoView = null
        }
        mInfoView?.findViewById<TextView>(R.id.tv_email)?.text = item.email
        var infoNodeWidth = 220 + item.email.length*23
        if (infoNodeWidth < 700){
            infoNodeWidth = 700
        }
        val layoutParams = ConstraintLayout.LayoutParams(infoNodeWidth,infoNodeHeight)
        if (item.x > 0){
            layoutParams.rightToRight = item.viewId
        }else{
            layoutParams.leftToLeft = item.viewId
        }

        if (item.y > 0){
            layoutParams.bottomToBottom = item.viewId
        }else{
            layoutParams.topToTop = item.viewId
        }

        mInfoView?.layoutParams = layoutParams
        listCityView?.addView(mInfoView)
    }


    private fun createWebDialog(url: String){
        if (webDialog != null){
            webDialog?.dismiss()
            webDialog = null
        }
        webDialog = WebDialog.WebDialogBuilder(requireContext())
                .setUrl(url)
                .create()
        webDialog?.show()
    }

    private fun createImageDialog(url: String){
        if (imageDialog != null){
            imageDialog?.dismiss()
            imageDialog = null
        }
        imageDialog = ImageDialog.ImageDialogBuilder(requireContext())
                .setUrl(url)
                .create()
        imageDialog?.show()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        for (item in cityItemList){
            if (item.viewId == v?.id){
                when(item.type){
                    0->{
                       createWebDialog(item.url)
                    }
                    1->{
                        showNodeInfo(item)
                    }
                    2->{
                        createImageDialog(item.url)
                    }
                    3->{
                        createImageDialog(item.url)
                    }
                }
            }
        }
    }


    private fun getLengthX(x: Int): Int{
        return if (x%2 == 0){
            if (x == 0){
                0
            }else{
                (abs(x)-1)*imageViewWidth + imageViewWidth
            }
        }else{
            (abs(x)-1)*imageViewWidth +imageViewWidth/2
        }
    }

    private fun getLengthY(y: Int): Int{
        return if(y == 0){
            0
        }else{
            (abs(y)-1)*imageViewWidth + imageViewWidth/2 + (abs(y))*imageViewWidth/4
        }
    }

}