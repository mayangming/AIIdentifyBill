package com.aidiscern.bill

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.multidex.MultiDex
import com.aidiscern.bill.config.CommonConfig
import com.aidiscern.bill.config.HttpStatus
import com.aidiscern.bill.model.HttpResult
import com.aidiscern.bill.model.TokenModel
import com.aidiscern.bill.ocr.OCR
import com.google.gson.Gson
import http.OkHttpUtil
import http.callback.StringCallback
import okhttp3.Call
import java.lang.Exception

class AIBillApplication : Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        OkHttpUtil.initClient(null)
        initData()
    }

    private fun initData(){
        val params = mutableMapOf<String,String>()
        params["grant_type"] = "client_credentials"
        params["client_id"] = CommonConfig.BAIDU_AI_KEY
        params["client_secret"] = CommonConfig.BAIDU_AI_SECRET_KEY
        OCR.getToken(params,object : StringCallback(){
            override fun onError(call: Call?, e: Exception?, id: Int) {
                e?.printStackTrace()
                Toast.makeText(applicationContext,"应用key值和sceret无效，请联系商家进行更换",Toast.LENGTH_SHORT).show()
                CommonConfig.tokenModel = HttpResult(HttpStatus.ERROR,null)
            }

            override fun onResponse(response: String?, id: Int) {
                val tokenResult = Gson().fromJson(response,TokenModel::class.java)
                CommonConfig.tokenModel = HttpResult(HttpStatus.SUCCESS,tokenResult)
                val tokenModel = CommonConfig.tokenModel.data as TokenModel
                Log.e("YM","获取的token${tokenModel.access_token}")
                Log.e("YM","获取的token${tokenModel.expires_in}")
            }
        })
    }

}