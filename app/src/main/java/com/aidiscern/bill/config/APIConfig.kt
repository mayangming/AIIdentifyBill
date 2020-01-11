package com.aidiscern.bill.config

/** api配置文件  */
class APIConfig{
    companion object{
        /**
         * grant_type： 必须参数，固定为client_credentials；
         * client_id： 必须参数，应用的API Key；
         * client_secret： 必须参数，应用的Secret Key；
         * */
        const val token_url = "https://aip.baidubce.com/oauth/2.0/token"

        /** 火车票 */
        const val train_ticket_url = "https://aip.baidubce.com/rest/2.0/ocr/v1/train_ticket"

        /** 定额发票 */
        const val quota_invoice_url = "https://aip.baidubce.com/rest/2.0/ocr/v1/quota_invoice"

        /** 通用发票 */
        const val receipt_url = "https://aip.baidubce.com/rest/2.0/ocr/v1/receipt"

        /** 出租车发票 */
        const val taxi_url = "https://aip.baidubce.com/rest/2.0/ocr/v1/taxi_receipt"
    }
}