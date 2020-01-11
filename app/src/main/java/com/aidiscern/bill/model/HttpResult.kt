package com.aidiscern.bill.model

import com.aidiscern.bill.config.HttpStatus

/** http请求的基类 */
open class HttpResult<T>(
    val httpStatus: HttpStatus = HttpStatus.ERROR,//请求状态错误
    val data: T? = null)