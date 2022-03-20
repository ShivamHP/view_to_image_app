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
        intent.putExtra(Intent.EXTRA_TEXT, "Click the link to know more..\n https://github.com/ShivamPachchigar14112/view_to_image_app");
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
        intent.type = "image/*"
        startActivity(Intent.createChooser(intent, "Share with"))
    }
}