package com.rikkei.tra_02_t0103intent_intentfilter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.rikkei.tra_02_t0103intent_intentfilter.constant.Define
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = MainActivity::class.java.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        btnOpenCameraVideo.setOnClickListener(this)
        onSharedIntent()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            btnOpenCameraVideo.id -> {
                askForPermission()
                openCameraVideo()
            }
        }
    }

    private fun openCameraVideo() {
        val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        i.putExtra(MediaStore.EXTRA_DURATION_LIMIT, Define.DURATION)
        startActivityForResult(i, 0)
    }

    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                Define.MY_PERMISSIONS_REQUEST_ACCOUNTS
            )
        }
    }

    private fun onSharedIntent() {
        val intent = intent
        val action = intent.action
        val type = intent.type
        if (action == Intent.ACTION_SEND && type != null) {
            when {
                type.startsWith("image/") -> {
                    val imageUri = intent
                        .getParcelableExtra(Intent.EXTRA_STREAM) as Uri
                    ivImageShare.apply {
                        visibility = View.VISIBLE
                        setImageURI(imageUri)
                    }

                }
                type.startsWith("video/") -> {
                    val videoUri = intent
                        .getParcelableExtra(Intent.EXTRA_STREAM) as Uri
                    videoShared.apply {
                        visibility = View.VISIBLE
                        setVideoPath(videoUri.toString())
                    }
                }
                else -> Log.d(TAG, "error")
            }
        }
    }
}
