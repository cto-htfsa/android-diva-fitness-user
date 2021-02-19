package com.htf.diva.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import com.htf.diva.base.MyApplication
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    fun loadImageCrop(url: String?, imageView: ImageView, placeHolder: Int, targetHeight: Int, targetWidth: Int) {
        //        System.out.println("IMAGE URL IS= " + url);
        if (url != null && !url.isEmpty()) {
            /* Picasso.get().load(IMAGE_URL + url).resize(targetWidth, targetHeight).centerCrop().placeholder(placeHolder)
                 .into(imageView)*/
        } else {
            /*Picasso.get().load(placeHolder).resize(targetWidth, targetHeight).centerCrop().into(imageView)*/
        }
    }

    fun loadImageInside(url: String?, imageView: ImageView, placeHolder: Int, targetHeight: Int, targetWidth: Int) {
        //        System.out.println("IMAGE URL IS= " + url);
        if (url != null && !url.isEmpty() && !url.equals("null", ignoreCase = true)) {
            /* Picasso.get().load(IMAGE_URL + url).resize(targetWidth, targetHeight).centerInside()
                 .placeholder(placeHolder).into(imageView)*/
        } else {
            /*Picasso.get().load(placeHolder).resize(targetWidth, targetHeight).centerInside().into(imageView)*/
        }
    }

    fun setPicassoImage(url: String?,view: ImageView,placeHolder: Int){

        Picasso.get().load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .placeholder(placeHolder)
            .into(view,object: Callback {
                override fun onSuccess() {
                    Picasso.get().load(url).placeholder(placeHolder).into(view)
                }
                override fun onError(e: java.lang.Exception?) {
                    Picasso.get().load(url).placeholder(placeHolder).into(view)

                }
            })
    }

    fun getMimeType(file: File): String? {
        var mimeType: String? = null
        val uri= Uri.fromFile(file)
        mimeType = if (uri?.scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            val cr: ContentResolver = MyApplication.getAppContext().contentResolver
            cr.getType(uri!!)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri
                    .toString()
            )
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.toLowerCase()
            )
        }
        return mimeType
    }

    fun getMimeTypeExt(url: String?): String? {
        if (url != null && url != "") {
            try {
                var type: String? = null
                val extension = url.substring(url.lastIndexOf(".") + 1, url.length)
                Log.i("extension", "ext : $extension")
                if (extension != null) {
                    type = extension
                }
                return type
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return null
    }



    fun getFileSize(currActivity: Activity, file:File):Long{
        var dataSize=0
        var size:Long=0
        val scheme=Uri.fromFile(file).scheme!!
        val uri=Uri.fromFile(file)
        if(scheme.equals(ContentResolver.SCHEME_CONTENT))
        {
            try {
                val fileInputStream=currActivity.contentResolver.openInputStream(uri!!);
                dataSize = fileInputStream!!.available();
            } catch (e:Exception) {
                e.printStackTrace();
            }
            System.out.println("File size in bytes"+dataSize);

            size=dataSize.toLong()

        }
        else if(scheme.equals(ContentResolver.SCHEME_FILE))
        {
            var f:File?=null
            val path = uri?.path
            try {
                f =File(path!!)
            } catch (e:Exception) {
                e.printStackTrace();
            }
            System.out.println("File size in bytes"+f!!.length());

            size=f.length()
        }

        return size

    }

    fun getAllShownImagesPath(activity: Activity): ArrayList<String> {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        cursor = activity.contentResolver.query(
            uri, projection, null,
            null, null
        )
        column_index_data = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)!!
        column_index_folder_name = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor?.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        return listOfAllImages
    }

    fun getFileNameForAWS(file: File):String{
        val fileName = file.name
        val extension = fileName.substring(fileName.lastIndexOf("."), fileName.length)
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
        Log.d("getFileNameForAWS",AppSession.deviceId + timeStamp + extension)
        return AppSession.deviceId + timeStamp + extension
    }

    fun getFileExtension(file: File):String{
        return file.name.substring(file.name.lastIndexOf("."), file.name.length)
    }

    fun isFileDownloaded(context: Context, fileName: String):Boolean{
        val tempFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            "$fileName"
        )
        Log.e("FILE PATH1234", "File Path:$tempFile")
        return tempFile.exists()
    }

    fun getDestinationFilePath(currActivity: Activity, name: String, folderName: String): File?{
        var mediaStorageDir: File?=null
        mediaStorageDir= File(
            currActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/Media"),
            folderName
        )
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        return File(mediaStorageDir.path + File.separator + name)

    }

    @Throws(IOException::class)
    fun makeCopyFile(src: File?, dst: File?) {
        FileInputStream(src!!).use { `in` ->
            FileOutputStream(dst!!).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    fun compressImageFile(photo: File):File{
        val bitmap= BitmapFactory.decodeFile(photo.path)
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val stream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
        return photo
    }


}