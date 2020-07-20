package co.trell.aniruddh

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import co.trell.aniruddh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myData: MyData = MyData("Select Video")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.myData = myData

        binding.selectVideoButton.setOnClickListener {
            val videoPickerIntent = Intent(Intent.ACTION_PICK)
            videoPickerIntent.setType("video/*")
            startActivityForResult(Intent.createChooser(videoPickerIntent, "Pick video using"), 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 101) {
                val uri: Uri? = data.data
                val selectedVideo: String = getPath(uri)
                if (selectedVideo!= null) {
                    Toast.makeText(this, selectedVideo, Toast.LENGTH_SHORT).show()
                    val playerIntent = Intent(this, PlayerActivity::class.java)
                    playerIntent.putExtra("videoUri", uri.toString())
                    playerIntent.putExtra("videoPath", selectedVideo)
                    startActivity(playerIntent)
                }
            }
        }
    }

    private fun getPath(uri: Uri?): String {
        val projectionArray = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? = uri?.let { applicationContext.contentResolver.query(it, projectionArray, null, null, null) }
        return if (cursor!=null) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } else {
            ""
        }
    }
}