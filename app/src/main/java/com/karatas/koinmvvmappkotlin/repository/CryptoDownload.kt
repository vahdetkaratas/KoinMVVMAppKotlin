package com.karatas.koinmvvmappkotlin.repository

import com.karatas.koinmvvmappkotlin.model.CryptoModel
import com.karatas.koinmvvmappkotlin.util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos() : Resource<List<CryptoModel>>
}