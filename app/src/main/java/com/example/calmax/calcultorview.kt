package com.example.calmax

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalcultorView : ViewModel(){

    private val _equationText = MutableLiveData("")
    val equationText: MutableLiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: LiveData<String> = _resultText

    fun onButtonClick(btn: String){
        Log.i("cliked Button", btn)

        _equationText.value?.let {
            if(btn=="AC"){
                _equationText.value = ""
                _resultText.value = "0"
                return

            }
            if(btn=="C"){
                _equationText.value = it.substring(0, it.length-1)
                return
            }
            if(btn=="="){
                _equationText.value = _resultText.value
                return
            }

            _equationText.value = it + btn

            try{
                _resultText.value = calculateResult(_equationText.value.toString())
            }catch (_ : Exception){}


        }
    }
    fun calculateResult(equation: String): String{
        val rhino: Context = Context.enter()
        rhino.optimizationLevel = -1
        val scriptable : Scriptable = rhino.initStandardObjects()
        val result = rhino.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        return result
    }
}