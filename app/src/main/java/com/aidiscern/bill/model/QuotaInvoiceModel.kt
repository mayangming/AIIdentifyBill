package com.aidiscern.bill.model

//定额发票识别结果
class QuotaInvoiceModel(
    val log_id: Long = 0,//唯一的log id，用于问题定位
    val words_result_num: Int = 0,//识别结果数，表示words_result的元素个数
    val words_result:WordsResult = WordsResult()//识别结果数组
){
    class WordsResult(
        val invoice_code: String = "",//发票代码
        val invoice_number: String = "",//发票号码
        val avg_score: String = "",//均值
        val invoice_rate: String = ""//金额
    )
}

