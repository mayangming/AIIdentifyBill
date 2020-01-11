package com.aidiscern.bill.model

//出租车票识别结果
class TaxiReceiptModel(
    val log_id: Long = 0,//唯一的log id，用于问题定位
    val words_result_num: Int = 0,//识别结果数，表示words_result的元素个数
    val words_result:WordsResult = WordsResult()//识别结果数组
){
    class WordsResult(
        val InvoiceCode: String = "",//发票代号
        val InvoiceNum: String = "",//发票号码
        val TaxiNum: String = "",//车牌号
        val Date: String = "",//日期
        val Time: String = "",//上下车时间
        val Fare: String = "",//金额
        val FuelOilSurcharge: String = "",//燃油附加费
        val TotalFare: String = ""//实收金额
    )
}
