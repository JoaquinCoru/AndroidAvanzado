package com.keepcoding.androidavanzado.ui.commons

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineViewModel: ViewModel() {

    fun tareaCostosa(){
        Thread.sleep(100000)
    }

    fun miThreadConCallback(){
        val resultado = devuelveParametro()

        Log.d("HOLA", "ANTES")
        miFuncionConCallback {
            Log.d("HOLA", it)
        }
        Log.d("HOLA", "DESPUES")
    }

    fun devuelveParametro(): String {
        return "Hola"
    }

    fun miFuncionConCallback(callback: (String)->(Unit)){
        val thread = Thread(Runnable {
            Thread.sleep(1000)
            callback("HOLA")
        })
        thread.start()
    }

    fun miPrimeraCorrutina(){
        GlobalScope.launch {
            Log.d("HOLA", "START de corrutina")
            Thread.sleep(5000)
            Log.d("HOLA", "DESPUES de corrutina")
            //Actualizar UI
        }
    }

    fun miCorrutinaEnMain(){
        GlobalScope.launch(Dispatchers.Main) {

        }
    }

    fun miCorrutinaEnIO(){
        val job = GlobalScope.launch(Dispatchers.IO) {

        }
    }

    fun devuelveHolaDentroDe10Segundos(){
        GlobalScope.launch {
            val hola: String = withContext(Dispatchers.IO){
                delay(10000)
                "HOLA"
            }

            Log.d("HOLA", hola)
        }
    }

    fun devuelveVariosResultados(){
        GlobalScope.launch {
            val result1 = async(Dispatchers.IO) { funcionXSegundos(2) }
            val result2 = async(Dispatchers.IO) { funcionXSegundos(4) }

            Log.d("HOLA", "FINAL ${result1.await()} ${result2.await()}")
        }
    }

    fun corrutinasComplejas(){
        GlobalScope.launch {
            // Resultado String a los 3 segundos

            // OPCION 1: ASYNC
            val result1 = async(Dispatchers.IO) {
                funcionXSegundos(3)
            }
            Log.d("HOLA", "Result 1: ${result1.await()}")

            // OPCION 2: WITH CONTEXT -> MAS CORRECTA
            val result2 = withContext(Dispatchers.IO) {
                funcionXSegundos(3)
            }
            Log.d("HOLA", "Result 2: ${result2}")
        }
    }

    fun corrutinasComplejas2(){
        GlobalScope.launch {
            // Resultado String a los 3 segundos

            Log.d("ADIOS", "INICIO") // T = 0
            val result1 = async(Dispatchers.IO) {
                funcionXSegundos(3)
            }
            Log.d("ADIOS", "Result 1: $result1") // T = 0
            Log.d("ADIOS", "Result 1 bis: ${result1.await()}") // T = 3

            val result2 = withContext(Dispatchers.IO) {
                funcionXSegundos(2)
            }
            Log.d("ADIOS", "Result 2: ${result2}") // T = 5

            val result3 = withContext(Dispatchers.IO) {
                funcionDevuelveXSegundos(2)
            }
            Log.d("ADIOS", "Result 3: ${result3}") // T = 7
            Log.d("ADIOS", "Result 1: ${result1.await()} Result 2: ${result2}") // T = 7

            val result4 = async(Dispatchers.IO) {
                funcionDevuelveXSegundos(result3)
            }

            Log.d("ADIOS", "Result 1: ${result1.await()} Result 2: ${result2} Result 3: $result3 Result 4: ${result4.await()}") // T = 9

        }
    }

    suspend fun funcionXSegundos(segundos:Int): String {
        Log.d("HOLA", "HOLA$segundos inicio")
        delay(segundos * 1000L)
        Log.d("HOLA", "HOLA$segundos Final")
        return "HOLA $segundos"
    }

    suspend fun funcionDevuelveXSegundos(segundos:Int): Int {
        Log.d("HOLA", "HOLA$segundos inicio")
        delay(segundos * 1000L)
        Log.d("HOLA", "HOLA$segundos Final")
        return segundos
    }


}
