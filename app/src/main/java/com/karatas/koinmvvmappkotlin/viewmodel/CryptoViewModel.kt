package com.karatas.koinmvvmappkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karatas.koinmvvmappkotlin.model.CryptoModel
import com.karatas.koinmvvmappkotlin.repository.CryptoDownload
import com.karatas.koinmvvmappkotlin.util.Resource
import kotlinx.coroutines.*


class CryptoViewModel(
    private val cryptoDownloadRepository : CryptoDownload
) : ViewModel() {

    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "error!",data = true)
    }




    fun getDataFromAPI() {
        cryptoLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("",data = false)
                    cryptoList.value = resource
                }
            }
        }

        /*
       val BASE_URL = "https://raw.githubusercontent.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)



        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cryptoError.value = false
                    cryptoLoading.value = false
                    response.body()?.let {
                        cryptoList.value = it
                        }
                    }
                }
            }
        }

        */
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}