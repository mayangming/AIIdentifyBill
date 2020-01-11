package com.aidiscern.bill

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.first.*

class FistActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)
        button.setOnClickListener {
            val intent = Intent(it.context,MainActivity::class.java)
            startActivity(intent)
        }
    }
}