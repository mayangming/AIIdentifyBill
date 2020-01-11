package com.aidiscern.bill.model

class ErrorModel(
    /**
     * invalid_client	unknown client id	API Key不正确
     * invalid_client	Client authentication failed	Secret Key不正确
     */
      val error: String = "",//错误码；关于错误码的详细信息请参考下方鉴权认证错误码。
      val error_description: String = ""//错误描述信息，帮助理解和解决发生的错误。
)