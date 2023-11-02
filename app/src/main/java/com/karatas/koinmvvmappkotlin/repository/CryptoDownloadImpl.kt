package com.karatas.koinmvvmappkotlin.repository

import com.karatas.koinmvvmappkotlin.model.CryptoModel
import com.karatas.koinmvvmappkotlin.service.CryptoAPI
import com.karatas.koinmvvmappkotlin.util.Resource
import kotlinx.coroutines.*

class CryptoDownloadImpl(private val api : CryptoAPI) : CryptoDownload {

    override suspend fun downloadCryptos() : Resource<List<CryptoModel>> {
        return try {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }
}