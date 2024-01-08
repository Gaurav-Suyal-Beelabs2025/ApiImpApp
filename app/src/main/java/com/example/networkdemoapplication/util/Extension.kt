package com.example.networkdemoapplication.util

import android.Manifest.permission.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.os.Environment
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Ritik on 5/4/2023.
 */
object Extension {

    fun formatToDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

        val dateTime = inputFormat.parse(inputDate)
        return outputFormat.format(dateTime)
    }

    fun convertDateString(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }

    fun formatDate(date: String, format: String): String {
        val date1 = SimpleDateFormat(format).parse(date)
         val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
         return dateFormat.format(date1)
    }


    fun String.fromDateToLong(): Long {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = dateFormat.parse(this)
            parsedDate?.time ?: 0L
        } catch (ex: Exception) {
            0L
        }
    }
    /**
     * Format Address
     */
     fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val addressLines = address.getAddressLine(0).split(",").filterIndexed { index, _ -> index > 0 }
                return addressLines.joinToString(",")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "Address not found"
    }


    fun captureScrollViewAsBitmap(scrollView: ScrollView): Bitmap {
        val totalHeight = scrollView.getChildAt(0).height
        val totalWidth = scrollView.getChildAt(0).width
        val bitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

    fun saveBitmapAsPdf(context: Context, bitmap: Bitmap, fileName:String): File? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        pdfDocument.finishPage(page)
        val pdfFileName = "${fileName}_${System.currentTimeMillis()}.pdf"
         val publicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
              if (!publicDirectory.exists()) {
            publicDirectory.mkdirs()
        }
        val pdfFile = File(publicDirectory, pdfFileName)
        try {
            val fileOutputStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(fileOutputStream)
            fileOutputStream.close()
            pdfDocument.close()
            displayPdfFile(context,pdfFile)
            return pdfFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun displayPdfFile(context: Context, pdfFile: File) {
        val pdfFileUri = FileProvider.getUriForFile(
            context,
            "com.example.rubyhospital.fileprovider",
            pdfFile
        )
        val pdfIntent = Intent(Intent.ACTION_VIEW)
         pdfIntent.putExtra(Intent.EXTRA_STREAM, pdfFileUri)
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        pdfIntent.setDataAndType(pdfFileUri, "application/pdf")
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val chooser = Intent.createChooser(pdfIntent, "Open PDF with")
        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK // optional
            context.startActivity(pdfIntent)
    }


    fun downloadPdfFile(context: Context, pdfFileUri: Uri, fileName: String) {
        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "${fileName}_${System.currentTimeMillis()}.pdf"
        val downloadFile = File(downloadDirectory, fileName)

        try {
            val inputStream = context.contentResolver.openInputStream(pdfFileUri)
            val outputStream = FileOutputStream(downloadFile)
            inputStream?.use { input ->
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024)
                    var bytesRead: Int
                    while (input.read(buffer).also { bytesRead = it } >= 0) {
                        output.write(buffer, 0, bytesRead)
                    }
                }
            }

            Toast.makeText(context, "Downloaded PDF to ${downloadFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to download PDF.", Toast.LENGTH_SHORT).show()
        }

    }

    fun String.fromRawDateToTimeLong(): Long {
        try {
            val rawFormat = "yyyy-MM-dd'T'HH:mm:ss"
            val dateFormatter = SimpleDateFormat(rawFormat, Locale.getDefault())
            val date = dateFormatter.parse(this)
            return date?.time ?: 0L
        } catch (ex: Exception) {
            return 0L
        }
    }
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}