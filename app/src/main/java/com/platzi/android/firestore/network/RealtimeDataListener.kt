package com.platzi.android.firestore.network

import java.lang.Exception
import com.google.firebase.firestore.core.UserData.ParsedUpdateData

interface RealtimeDataListener<T>{

    fun onDataChange(updateData: T)
    fun onError(exception: Exception)
}