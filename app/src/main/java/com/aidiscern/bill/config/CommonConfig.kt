package com.aidiscern.bill.config

import com.aidiscern.bill.model.HttpResult
import com.aidiscern.bill.model.TokenModel

/** 通用配置文件 */
class CommonConfig{
    companion object{
        //*************************百度识别的key****************************
        //appId
        const val BAIDU_AI_APPID = "17764376"
        //Key
        const val BAIDU_AI_KEY = "h4LpkRpALBqdxdlwv1kEUbi0"
        //secret key
        const val BAIDU_AI_SECRET_KEY = "LxfVlKV3bhbCQVA7CACG1SAY1UGwFbQU"

        //*************************文件相关信息****************************
        //文件裁剪的名字
        const val SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage"
        const val PICTURE_FORMAT_JPG = ".jpg"
        const val PICTURE_FORMAT_PNG = ".png"

        //*************************全局信息****************************
        var tokenModel = HttpResult<TokenModel>()//token信息
    }
}