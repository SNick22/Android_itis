package com.example.myapplication.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.presentation.App

fun Context.appComponent(): AppComponent =
    when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent()
    }

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T
) = viewModels<T> {
    MainVMFactory(this, create)
}
