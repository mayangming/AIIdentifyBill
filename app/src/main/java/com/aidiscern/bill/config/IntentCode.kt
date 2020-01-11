package com.aidiscern.bill.config

/** intent请求码和响应码 */
class IntentCode {
    companion object{
        /** 图片选择的请求码 */
        const val REQUEST_CODE_CHOOSE = 23

        /** 通用发票请求码 */
        const val REQUEST_CODE_RECEIPT = 10

        /** 定额发票的请求码 */
        const val REQUEST_CODE_QUOTA_INVOIC = 11

        /** 火车票的请求码 */
        const val REQUEST_CODE_TRAIN_RECEIPT = 12

        /** 出租车的请求码 */
        const val REQUEST_CODE_TAXIi_RECEIPT = 13
    }
}