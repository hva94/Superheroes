package com.hvasoft.superheroes.data.network

import android.content.Context
import com.hvasoft.superheroes.R

class ApiKeyProvider(private val context: Context) {

    fun getApiKey(): String {
        return context.getString(R.string.api_key)
    }

}