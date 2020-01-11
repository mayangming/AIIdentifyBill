package com.aidiscern.bill.model

//通用发票识别结果
class ReceiptModel(
    val log_id: Long = 0,//唯一的log id，用于问题定位
    val words_result_num: Int = 0,//识别结果数，表示words_result的元素个数
    val wordsResult:List<WordsResult> = listOf()//识别结果数组
){
    class WordsResult(
        val words: String = "",//识别结果字符串
        val location: Location = Location()//位置数组（坐标0点为左上角）
    )

    class Location(
        val left: Int = 0,//表示定位位置的长方形左上顶点的水平坐标
        val top: Int = 0,//表示定位位置的长方形左上顶点的垂直坐标
        val width: Int = 0,//表示定位定位位置的长方形的宽度
        val height: Int = 0//表示位置的长方形的高度
    )

}