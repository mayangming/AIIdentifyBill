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
import kotlinx.android.synthetic.main.fragment_receipt.*

/** 通用发票 */
class ReceiptFragment : Fragment(){
    private val args: ReceiptFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_receipt,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receiptData = args.receptData
        if (TextUtils.isEmpty(receiptData)) {
            return
        }
        Log.e("YM","获取的数据:${receiptData}")
        receipt_result.text = receiptData
    }

}