package com.ruiting.pdftest.extensions

import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File

/**
 * @ClassName PDFView
 * @Description TODO
 * @Author WangShunYi
 * @Date 2019-08-27 10:45
 */
fun PDFView.showPDF(file: File?, onInitiallyRenderedBack: () -> Unit) {
    file ?: return

    fromFile(file).onRender {
        onInitiallyRenderedBack()
    }.defaultPage(0)
        .enableAntialiasing(true)
        .spacing(10)
        .swipeHorizontal(false)
        .pageSnap(true)
        .autoSpacing(true)
        .pageFling(true)
        .pageFitPolicy(FitPolicy.WIDTH)
        .load()

}