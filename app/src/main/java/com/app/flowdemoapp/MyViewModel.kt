package com.app.flowdemoapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {

    //Producer
    val myFlow = flow {
        for (i in 1..100){
            emit(i)
            delay(1000)
        }
    }

    init {
        backPressure()
    }

    private fun backPressure(){
        //Producer
        val myFlow1 = flow {
            for (i in 1..10){
                Log.i("Tag", "Produced $i")
                emit(i)
                delay(1000)
            }
        }

        viewModelScope.launch {
            myFlow1
                .filter{i -> i%3 == 0}
                .collect{
                Log.i("Tag", "Consumed $it")
            }
        }
    }
}