package com.example.drawexample.base

interface BaseContract {
    interface View

    interface Presenter<in T>{
        fun onAttach(view : T)
    }
}