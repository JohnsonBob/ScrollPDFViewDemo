package com.ruiting.pdftest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.xutils.x

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        x.Ext.init(application)

        pdfView.scrollLoadPdf("http://10.10.20.240:8136/uploads/20190930/004ae17a7c4bc733870e4fc87d6638dc.pdf"){
            //播放完成一次监听
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfView.stopScrollPage()
    }
}
