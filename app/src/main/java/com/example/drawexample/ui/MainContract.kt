package com.example.drawexample.ui

import android.graphics.Bitmap
import com.example.drawexample.base.BaseContract

interface MainContract {
    interface View : BaseContract.View{
        fun clearCanvas()
        fun onShowToast(message : String)
        fun getDrawingArea() : Bitmap
        fun printImage(image : Bitmap)
    }
    interface Presenter : BaseContract.Presenter<View>{
        fun onUploadImage()
        fun onSaveImageToAlbum()
        fun onClearCanvas()
        fun onPrintImage()
    }
}