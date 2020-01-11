package com.aidiscern.bill.model

/** 获取的token返回结果 */
class TokenModel(val access_token: String = "",//要获取的Access Token
                 val expires_in: String = ""//Access Token的有效期(秒为单位，一般为1个月)
                )