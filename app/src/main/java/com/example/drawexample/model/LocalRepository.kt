package com.example.drawexample.model

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import javax.inject.Inject

class LocalRepository @Inject constructor(val context: Context) {
    fun getCacheDir() = context.cacheDir!!

    fun getAlbumDir(albumName: String): File {
        val file = File(context.filesDir, albumName)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.d("LocalRepository", "Directory Not Created")
            }
        }
        return file
    }
}