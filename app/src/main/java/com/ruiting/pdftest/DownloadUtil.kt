package com.ruiting.pdftest

import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.text.TextUtils
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File
import java.util.*


/**
 * @ClassName DownloadUtil
 * @Description TODO
 * @Author WangShunYi
 * @Date 2019-08-26 20:28
 */
object DownloadUtil {

    fun download(mDownloadUrl: String?, callback: (file: File?) -> Unit) {
        if (TextUtils.isEmpty(mDownloadUrl)) {
            return
        }
        if (Environment.getExternalStorageState() == MEDIA_MOUNTED) {
            val path =
                Environment.getExternalStorageDirectory().absolutePath + File.separator +
                    "pdftest" + File.separator

            val fileName = "${Date().time}.pdf"
            //删除已经存在的文件
            if (File(path).exists()) File(path).listFiles().forEach { it.delete() }

            // mDownloadUrl为JSON从服务器端解析出来的下载地址
            val requestParams = RequestParams(mDownloadUrl)
            // 为RequestParams设置文件下载后的保存路径
            requestParams.saveFilePath = path + fileName
            // 下载完成后自动为文件命名
            requestParams.isAutoRename = true
            x.http().get(requestParams, object : Callback.ProgressCallback<File> {
                override fun onLoading(total: Long, current: Long, isDownloading: Boolean) = Unit
                override fun onStarted() = Unit
                override fun onWaiting() = Unit
                override fun onCancelled(cex: Callback.CancelledException?) = Unit
                override fun onFinished() = Unit
                override fun onSuccess(result: File?) {
                    callback(result)
                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                    callback(null)
                }
            })
        }
    }


}