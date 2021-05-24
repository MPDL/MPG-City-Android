package de.mpg.mpdl.mpgcity.mvvm.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import de.mpg.mpdl.mpgcity.mvvm.ui.activity.MainActivity
import timber.log.Timber
import java.io.IOException
import java.util.*

class CustomArFragment : ArFragment() {
    var mAugmentedImageMap: HashMap<Bitmap, String> = HashMap()

    override fun getSessionConfiguration(session: Session?): Config {
        planeDiscoveryController.setInstructionView(null)
        val config = Config(session)
        config.lightEstimationMode = Config.LightEstimationMode.DISABLED
        config.planeFindingMode = Config.PlaneFindingMode.DISABLED
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        config.focusMode = Config.FocusMode.AUTO
        session!!.configure(config)
        arSceneView.setupSession(session)
        arSceneView.planeRenderer.isVisible = false

        if (setupAugmentedImagesDb(config, session)) {
            Timber.d("SetupAugImgDb: Success")
        } else {
            Timber.e("SetupAugImgDb: Faliure setting up db")
        }
        return config
    }

    private fun setupAugmentedImagesDb(config: Config, session: Session?): Boolean {
        val augmentedImageDatabase = AugmentedImageDatabase(session)
        for (bitmap in mAugmentedImageMap.keys) {
            bitmap.recycle()
        }
        mAugmentedImageMap.clear()
        for (name in MainActivity.SCAN_IMAGE_NAMES){
            val bitmap = loadAugmentedImage("$name.png")
            mAugmentedImageMap[bitmap!!] = name
            augmentedImageDatabase.addImage(name, bitmap,0.3f)
        }
        config.augmentedImageDatabase = augmentedImageDatabase
        return true
    }

    private fun loadAugmentedImage(name_img: String): Bitmap? {
        try {
            requireContext().assets.open(name_img)
                .use { `is` -> return BitmapFactory.decodeStream(`is`) }
        } catch (e: IOException) {
            Log.e("ImageLoad", "IO Exception", e)
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        for (bitmap in mAugmentedImageMap.keys) {
            bitmap.recycle()
        }
        mAugmentedImageMap.clear()
    }

}