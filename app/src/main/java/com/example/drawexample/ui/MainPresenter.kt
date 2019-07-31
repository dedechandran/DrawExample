package com.example.drawexample.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.print.PrintHelper
import com.example.drawexample.model.LocalRepository
import com.example.drawexample.model.RemoteRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import javax.inject.Inject

class MainPresenter @Inject constructor(val context: Context) : MainContract.Presenter {
    @Inject
    lateinit var localRepository: LocalRepository
    @Inject
    lateinit var remoteRepository: RemoteRepository

    private lateinit var mainView: MainContract.View
    private lateinit var tempFile: File

    override fun onSaveImageToAlbum() {
        val albumDir = localRepository.getAlbumDir(ALBUM_DIRECTORY_NAME)
        val drawingArea = mainView.getDrawingArea()
        val storedPath = "$albumDir Signature"
        try {
            val fileOutputStream = FileOutputStream(storedPath)
            drawingArea.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            Log.d("MAINPRESENTER", "SAVE SUCCES")
            fileOutputStream.close()
        } catch (e: Exception) {
            mainView.onShowToast(e.message.toString())
        }
        Log.d("MAINPRESENTER", albumDir.toString())
    }

    private fun onSaveImageToCacheStorage() {
        val cacheDir = localRepository.getCacheDir()
        tempFile = File.createTempFile(TEMPORARY_FILE_NAME, null, cacheDir)
        val drawingArea = mainView.getDrawingArea()
        try {
            val fileOutputStream = FileOutputStream(tempFile)
            drawingArea.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            Log.d("MAINPRESENTER", "SAVE SUCCES")
            fileOutputStream.close()
        } catch (e: Exception) {
            mainView.onShowToast("FAILED SAVE TO CACHE STORAGE")
        }
    }

    override fun onUploadImage() {
        this.onSaveImageToCacheStorage()
        val requestFile = RequestBody.create(MediaType.parse("image/*"), tempFile)
        val body = MultipartBody.Part.createFormData("photo", tempFile.name, requestFile)
        remoteRepository.uploadImage(body, object : RemoteRepository.RemoteCallback<String> {
            override fun onSuccess(data: String) {
                mainView.onShowToast(data)
                Log.d("MAINPRESENTER", data)
            }

            override fun onError(t: Throwable) {
                Log.d("MAINPRESENTER", "FAILED UPLOADING")
                mainView.onShowToast("FAILED UPLOADING")
            }
        })
    }

    override fun onPrintImage() {
        val drawingArea = mainView.getDrawingArea()
        mainView.printImage(drawingArea)
    }

    override fun onAttach(view: MainContract.View) {
        mainView = view
    }

    override fun onClearCanvas() {
        mainView.clearCanvas()
    }


}