package com.aidiscern.bill.model

//火车票票识别结果
class TrainTicketModel(
    val log_id: Long = 0,//唯一的log id，用于问题定位
    val direction: Int = 0,//方向
    val words_result_num: Int = 0,//识别结果数，表示words_result的元素个数
    val words_result:WordsResult = WordsResult()//识别结果数组
){
    class WordsResult(
        val name: String = "",//乘客姓名
        val destination_station: String = "",//到达站
        val seat_category: String = "",//席别
        val ticket_rates: String = "",//车票金额
        val ticket_num: String = "",//车票号
        val seat_num: String = "",//车厢号
        val train_num: String = "",//车次号
        val starting_station: String = ""//始发站
    )
}
