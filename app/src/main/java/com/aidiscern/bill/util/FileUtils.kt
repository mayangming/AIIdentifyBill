package com.aidiscern.bill.util

import android.net.Uri
import android.util.Base64
import java.io.File
import java.net.URI

class FileUtils {
    companion object{

        fun convertToBase64(attachment: File): String {
            return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
        }

        fun convertToBase64(fileUri: Uri): String {
            val fileURI = URI(fileUri.toString())
            val file = File(fileURI)
            return convertToBase64(file)
        }
    }
}