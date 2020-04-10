package com.aidiscern.bill

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.aidiscern.bill.config.CommonConfig
import com.aidiscern.bill.config.IntentCode
import com.aidiscern.bill.expand.cropPhoto
import com.aidiscern.bill.expand.selectPhoto
import com.aidiscern.bill.fragment.ReceiptFragmentArgs
import com.aidiscern.bill.ocr.OCR
import com.aidiscern.bill.util.FileUtils
import com.aidiscern.bill.util.GetPathFromUri
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import http.callback.StringCallback
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import java.io.File
import java.net.URLEncoder

/** AI票据识别 */
class MainActivity : AppCompatActivity () {
    private lateinit var navController: NavController
    private var currentStatus = IntentCode.REQUEST_CODE_RECEIPT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData(){
        navController = findNavController(R.id.nav_host_fragment)
        initOnclick()
    }

    private fun initOnclick(){
        receipt_btn.setOnClickListener {
            checkPermissions()
            currentStatus = IntentCode.REQUEST_CODE_RECEIPT
        }
        quota_invoic_btn.setOnClickListener {
            checkPermissions()
            currentStatus = IntentCode.REQUEST_CODE_QUOTA_INVOIC

        }
        train_receipt_btn.setOnClickListener {
            checkPermissions()
            currentStatus = IntentCode.REQUEST_CODE_TRAIN_RECEIPT

        }
        taxi_receipt_btn.setOnClickListener {
            checkPermissions()
            currentStatus = IntentCode.REQUEST_CODE_TAXIi_RECEIPT
        }
        end.setOnClickListener {
            finish()
        }
    }

    /** 检查权限 */
    private fun checkPermissions(){
        val rxPermissions = RxPermissions(this)
        val disposable = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(
                { aBoolean: Boolean ->
                    if (aBoolean) {
                        selectPhoto(IntentCode.REQUEST_CODE_CHOOSE)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.permission_request_denied,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            ) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun startCrop(data: Intent,requestCode: Int){
        val picturePathString = Matisse.obtainPathResult(data)//获取string路径
        val picturePath = Matisse.obtainResult(data)//获取uri路径
        val filePath = picturePathString[0]
        val suffix = filePath.substring(filePath.lastIndexOf("."),filePath.length)
        val bitmapFormat = when(suffix){
            CommonConfig.PICTURE_FORMAT_JPG -> Bitmap.CompressFormat.JPEG
            CommonConfig.PICTURE_FORMAT_PNG -> Bitmap.CompressFormat.PNG
            else -> {
                Toast.makeText(this@MainActivity,"目前只支持对jpg和png格式图片的裁剪",Toast.LENGTH_SHORT).show()
                return
            }
        }
        val destinationUri = Uri.fromFile(File(cacheDir, CommonConfig.SAMPLE_CROPPED_IMAGE_NAME+suffix))
        cropPhoto(picturePath[0], destinationUri,requestCode,bitmapFormat)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == IntentCode.REQUEST_CODE_CHOOSE ) {
            data?.let {
                startCrop(it,currentStatus)
            }
        }else if(resultCode == UCrop.RESULT_ERROR){//裁剪错误
            val cropError = data?.let { UCrop.getError(it) }
            cropError?.printStackTrace()
        }else{
            if (null == data) return
            val resultUri = UCrop.getOutput(data)
            val path = GetPathFromUri.getPath(this@MainActivity,resultUri)
            Log.e("YM","裁剪后返回的路径:${path}")
            if (null == resultUri){
                return
            }
            val imgBase64 =  FileUtils.convertToBase64(resultUri)
            val imgURLEncoder = URLEncoder.encode(imgBase64, "UTF-8")
            Log.e("YM","图片的encoder码:${imgURLEncoder}")
            when(requestCode){
                IntentCode.REQUEST_CODE_RECEIPT -> getReceipt(imgURLEncoder)
                IntentCode.REQUEST_CODE_QUOTA_INVOIC -> getQuotaInvoice(imgURLEncoder)
                IntentCode.REQUEST_CODE_TRAIN_RECEIPT -> getTrainTick(imgURLEncoder)
                IntentCode.REQUEST_CODE_TAXIi_RECEIPT -> getTaxi(imgURLEncoder)
                else -> Toast.makeText(this@MainActivity,"不识别的票据信息",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTrainTick(imgEncoder: String){
        val params = mutableMapOf("image" to imgEncoder)
        OCR.getTrainTicket(params,object : StringCallback(){
            override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                e?.printStackTrace()
            }

            override fun onResponse(response: String?, id: Int) {
                val args = ReceiptFragmentArgs(response ?: "")
                navController.navigate(R.id.trainReceiptFragment,args.toBundle())
            }
        })
    }

    private fun getReceipt(imgEncoder: String){
        val params = mutableMapOf("image" to imgEncoder)
        OCR.getReceipt(params,object : StringCallback(){
            override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                e?.printStackTrace()
            }

            override fun onResponse(response: String?, id: Int) {
                val args = ReceiptFragmentArgs(response ?: "")
                navController.navigate(R.id.receiptFragment,args.toBundle())
            }
        })
    }
    private fun getQuotaInvoice(imgEncoder: String){
        val params = mutableMapOf("image" to imgEncoder)
        OCR.getQuotaInvoice(params,object : StringCallback(){
            override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                e?.printStackTrace()
            }

            override fun onResponse(response: String?, id: Int) {
                val args = ReceiptFragmentArgs(response ?: "")
                navController.navigate(R.id.quotaInvoicFragment,args.toBundle())
            }
        })
    }
    private fun getTaxi(imgEncoder: String){
        val params = mutableMapOf("image" to imgEncoder)
        OCR.getTaxi(params,object : StringCallback(){
            override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                e?.printStackTrace()
            }

            override fun onResponse(response: String?, id: Int) {
                val args = ReceiptFragmentArgs(response ?: "")
                navController.navigate(R.id.taxiReceiptFragment,args.toBundle())
            }
        })
    }

}