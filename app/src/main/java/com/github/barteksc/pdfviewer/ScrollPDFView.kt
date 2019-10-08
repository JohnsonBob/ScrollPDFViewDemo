package com.github.barteksc.pdfviewer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ruiting.pdftest.DownloadUtil
import com.ruiting.pdftest.extensions.showPDF
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @ClassName ScrollPDFView
 * @Description 自定义自动滚动pdfview
 * @Author WangShunYi
 * @Date 2019年10月08日11:13:02
 */
class ScrollPDFView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : PDFView(context, attrs) {

    private var disposable: Disposable? = null
    //    private var pdfFile: PdfFile? = null
    private var finishOneListener: (() -> Unit)? = null
    private var contentHeight = 0f

    //文档偏移的步长
    private val currentOffxy = 5
    //文档定时默认翻页时间
    private val pdfTime = 25L
    private var currentOffN = 0f


    /**
     * 滚动加载pdf
     * url 服务器地址
     * isAutoPlayer 是d否自动播放 默认自动播放
     * finishOne 当播放完成一次监听
     */
    fun scrollLoadPdf(url: String?, isAutoPlayer: Boolean = true, finishOne: (() -> Unit)? = null) {
        finishOneListener = finishOne
        DownloadUtil.download(url) {
            if (it == null) {
                Toast.makeText(context, "文件下载失败！", Toast.LENGTH_SHORT).show()
                return@download
            }
            showPDF(it) {
                if (isAutoPlayer) setAutoPage()
            }
        }
    }

    /**
     * 设置自动翻页
     */
    private fun setAutoPage() {
        disposable = Observable.interval(0, pdfTime, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                try {
                    moveTo(width.toFloat(), -currentOffN)
                    loadPages()
                    currentOffN += currentOffxy
                } catch (e: Exception) {
                    Log.d("---", "error")
                }
            }
    }

    override fun moveTo(offsetX: Float, offsetY: Float, moveHandle: Boolean) {
        super.moveTo(offsetX, offsetY, moveHandle)
        contentHeight = pdfFile.getDocLen(zoom)
        if (-contentHeight > offsetY) {
            stopFling()
            jumpTo(0)
            currentOffN = 0f
            finishOneListener?.invoke()
        }
    }

    /**
     * 停止滚动翻页
     */
    fun stopScrollPage() {
        disposable?.dispose()
        if (disposable?.isDisposed == false)
            disposable?.dispose()
    }


}