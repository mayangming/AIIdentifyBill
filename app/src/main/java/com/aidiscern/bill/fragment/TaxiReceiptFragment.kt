package com.aidiscern.bill.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aidiscern.bill.R
import com.aidiscern.bill.model.TaxiReceiptModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_taxi_receipt.*

/** 出租车发票 */
class TaxiReceiptFragment : Fragment(){
    private val args: TaxiReceiptFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_taxi_receipt,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receiptData = args.receptData
        Log.e("YM","获取的数据:${receiptData}")
        if (TextUtils.isEmpty(receiptData)) {
            return
        }
        val taxiReceipt = Gson().fromJson(receiptData,TaxiReceiptModel::class.java)

        val wordResult = taxiReceipt.words_result

        InvoiceCode.text = "发票代号:${wordResult.InvoiceCode}"
        InvoiceNum.text = "发票代号:${wordResult.InvoiceNum}"
        TaxiNum.text = "发票代号:${wordResult.TaxiNum}"
        Date.text = "发票代号:${wordResult.Date}"
        Time.text = "发票代号:${wordResult.Time}"
        Fare.text = "发票代号:${wordResult.Fare}"
        FuelOilSurcharge.text = "发票代号:${wordResult.FuelOilSurcharge}"
        TotalFare.text = "发票代号:${wordResult.TotalFare}"


    }

}