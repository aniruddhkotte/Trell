package co.trell.aniruddh

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import co.trell.aniruddh.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val videoUri =  Uri.parse(intent.getStringExtra("videoUri"))
        val videoPath = intent.getStringExtra("videoPath")

        val videoView = findViewById<VideoView>(R.id.video_view)
        videoView.setVideoURI(videoUri)
        videoView.start()
    }
}