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
import com.aidiscern.bill.model.TrainTicketModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_train_ticket.*

/** 火车票 */
class TrainTicketFragment : Fragment(){
    private val args: TrainTicketFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_train_ticket,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receiptData = args.receptData
        Log.e("YM","获取的数据:${receiptData}")
        if (TextUtils.isEmpty(receiptData)) {
            return
        }
        val trainTicket = Gson().fromJson(receiptData,TrainTicketModel::class.java)

        val wordResult = trainTicket.words_result

        name.text = "乘客姓名${wordResult.name}"
        destination_station.text = "到达站${wordResult.destination_station}"
        seat_category.text = "席别${wordResult.seat_category}"
        ticket_rates.text = "车票金额${wordResult.ticket_rates}"
        ticket_num.text = "车票号${wordResult.ticket_num}"
        seat_num.text = "车厢号${wordResult.seat_num}"
        train_num.text = "车次号${wordResult.train_num}"
        starting_station.text = "始发站${wordResult.starting_station}"

    }

}