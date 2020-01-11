package com.aidiscern.bill.ocr

import android.util.Log
import com.aidiscern.bill.config.APIConfig
import com.aidiscern.bill.config.CommonConfig
import com.aidiscern.bill.model.TokenModel
import http.OkHttpUtil
import http.callback.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull

/** ocr接口配置 */
class OCR {
    companion object{
        fun <T> getToken(params: Map<String,String>, callback: Callback<T>){
            OkHttpUtil.postFrom().url(APIConfig.token_url).params(params).build().execute(callback)
        }

        /** 获取火车票信息 */
        fun <T> getTrainTicket(params: MutableMap<String,String> = mutableMapOf(), callback: Callback<T>){
            val tokenModel = CommonConfig.tokenModel.data as TokenModel
            params["access_token"] = tokenModel.access_token

            val content = getPostStringContent(params)
            OkHttpUtil.postString().url(APIConfig.train_ticket_url).mediaType("application/x-www-form-urlencoded".toMediaTypeOrNull()).content(content).build().execute(callback)
        }
        /** 获取定额发票信息 */
        fun <T> getQuotaInvoice(params: MutableMap<String,String> = mutableMapOf(), callback: Callback<T>){
            val tokenModel = CommonConfig.tokenModel.data as TokenModel
            params["access_token"] = tokenModel.access_token

            val content = getPostStringContent(params)
            OkHttpUtil.postString().url(APIConfig.quota_invoice_url).mediaType("application/x-www-form-urlencoded".toMediaTypeOrNull()).content(content).build().execute(callback)
        }
        /** 获取通用发票信息 */
        fun <T> getReceipt(params: MutableMap<String,String> = mutableMapOf(), callback: Callback<T>){
            val tokenModel = CommonConfig.tokenModel.data as TokenModel
            params["access_token"] = tokenModel.access_token

            val content = getPostStringContent(params)
            OkHttpUtil.postString().url(APIConfig.receipt_url).mediaType("application/x-www-form-urlencoded".toMediaTypeOrNull()).content(content).build().execute(callback)
        }
        /** 获取出租车发票信息 */
        fun <T> getTaxi(params: MutableMap<String,String> = mutableMapOf(), callback: Callback<T>){
            val tokenModel = CommonConfig.tokenModel.data as TokenModel
            params["access_token"] = tokenModel.access_token

            val content = getPostStringContent(params)
            OkHttpUtil.postString().url(APIConfig.taxi_url).mediaType("application/x-www-form-urlencoded".toMediaTypeOrNull()).content(content).build().execute(callback)
        }

        private fun getPostStringContent(params: Map<String,String>): String{
            var content = ""
            for((key,value) in params){
                val temp = "${key}=${value}&"
                content += temp
            }
            Log.e("YM","拼接完的字符串:${content}")
            val lastIndex = content.lastIndexOf("&")
            content = content.substring(0,lastIndex)
            Log.e("YM","处理后的字符串:${content}")
            return content
        }

    }

}