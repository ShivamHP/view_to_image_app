package com.shivam.uv_app

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import com.shivam.uv_app.databinding.ActivityMainBinding
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.shareButton.setOnClickListener {
            val bitmap = binding.cardView.drawToBitmap(Bitmap.Config.ARGB_8888)
            shareBitmap(bitmap)

        }
    }

//    fun getBitmapFromView(view: View): Bitmap? {
//        //Define a bitmap with the same size as the view
//        val returnedBitmap =
//            Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
//        //Bind a canvas to it
//        val canvas = Canvas(returnedBitmap)
//        //Get the view's background
//        val bgDrawable: Drawable = view.getBackground()
//        if (bgDrawable != null) //has background drawable, then draw it on the canvas
//            bgDrawable.draw(canvas) else  //does not have background drawable, then draw white background on the canvas
//            canvas.drawColor(Color.WHITE)
//        // draw the view on the canvas
//        view.draw(canvas)
//        //return the bitmap
//        return returnedBitmap
//    }

    private fun shareBitmap(bitmap: Bitmap) {
        //---Save bitmap to external cache directory---//
        //get cache directory
        val cachePath = File(externalCacheDir, "my_images/")
        cachePath.mkdirs()

        //create png file
        val file = File(cachePath, "Image_123.png")
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //---Share File---//
        //get file uri
        val myImageFileUri: Uri =
            FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)

        //create a intent
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "Share with"))
    }
}