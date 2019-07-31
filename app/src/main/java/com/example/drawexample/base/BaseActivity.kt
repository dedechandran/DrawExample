package com.example.drawexample.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun <T : View> Activity.bindView(@IdRes resId : Int) : Lazy<T>{
        @Suppress("TypeParameterFindViewById")
        return lazy(LazyThreadSafetyMode.NONE){
            findViewById(resId) as T
        }
    }
}