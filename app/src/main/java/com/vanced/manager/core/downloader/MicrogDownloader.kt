package com.vanced.manager.core.downloader

import android.content.Context
import com.vanced.manager.R
import com.vanced.manager.utils.DownloadHelper.downloadProgress
import com.vanced.manager.utils.DownloadHelper.fuelDownload
import com.vanced.manager.utils.InternetTools.microg
import com.vanced.manager.utils.PackageHelper.install

object MicrogDownloader {

    private const val fileName = "microg.apk"
    private const val folderName = "microg"

    fun downloadMicrog(context: Context) {
        val url = microg.get()?.string("url") ?: ""
        fuelDownload(url, folderName, fileName, context, onDownloadComplete = {
            startMicrogInstall(context)
        }, onError = {
            downloadProgress.value?.downloadingFile?.postValue(context.getString(R.string.error_downloading, fileName))
        })

    }

    fun startMicrogInstall(context: Context) {
        downloadProgress.value?.installing?.postValue(true)
        downloadProgress.value?.postReset()
        install("${context.getExternalFilesDir(folderName)}/$fileName", context)
    }
}
