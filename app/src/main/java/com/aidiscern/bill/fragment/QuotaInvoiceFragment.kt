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
import com.aidiscern.bill.model.QuotaInvoiceModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_quota_invoice.*

/** 定额发票 */
class QuotaInvoiceFragment : Fragment(){
    private val args: QuotaInvoiceFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quota_invoice,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receiptData = args.receptData
        if (TextUtils.isEmpty(receiptData)) {
            return
        }
        val quotaInvoice  = Gson().fromJson(receiptData, QuotaInvoiceModel::class.java)
        val wordResult = quotaInvoice.words_result

        invoice_code.text = "发票代码:${wordResult.invoice_code}"
        invoice_number.text = "发票号码:${wordResult.invoice_number}"
        invoice_rate.text = "金额:${wordResult.invoice_rate}"
    }
}