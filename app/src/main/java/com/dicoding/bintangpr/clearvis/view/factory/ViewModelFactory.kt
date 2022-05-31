package com.dicoding.bintangpr.clearvis.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import com.dicoding.bintangpr.clearvis.view.login.LoginViewModel
import com.dicoding.bintangpr.clearvis.view.profile.ProfileViewModel
import com.dicoding.bintangpr.clearvis.view.splashscreen.SplashScreenViewModel

//class ViewModelFactory (private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
//                ProfileViewModel(pref) as T
//            }
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
//                LoginViewModel(pref) as T
//            }
//            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
//                SplashScreenViewModel(pref) as T
//            }
////            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> {
////                ProfileViewModel(pref) as T
////            }
////            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
////                MapsViewModel(pref) as T
////            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//}