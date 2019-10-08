#### 以循环滚动的方式加载PDF 

本Demo使用[AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer "AndroidPdfViewer 官网")加载PDF
> 以循环滚动的方式加载PDF,播放结束后调用一次回掉函数
```使用方式

启动播放：
pdfView.scrollLoadPdf("http://10.10.20.240:8136/uploads/20190930/004ae17a7c4bc733870e4fc87d6638dc.pdf"){
            //播放完成一次监听
        }
        
停止播放
pdfView.stopScrollPage()
```