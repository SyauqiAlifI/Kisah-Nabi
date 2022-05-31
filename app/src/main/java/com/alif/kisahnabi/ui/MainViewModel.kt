package com.alif.kisahnabi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alif.kisahnabi.data.KisahResponse
import com.alif.kisahnabi.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Throwable>()
    val kisahResponse = MutableLiveData<List<KisahResponse>>()

    fun getData(responseHandler: (List<KisahResponse>) -> Unit, errorHandler: (Throwable) -> Unit) {
        ApiClient.getApiService()
            .getKisahNabi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            })
    }
    fun getKisahNabi(){
        isLoading.value = true
        getData({
            isLoading.value = false
            kisahResponse.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}