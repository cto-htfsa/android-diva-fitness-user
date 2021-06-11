package com.htf.diva.auth.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.auth.viewModel.ProfileViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.databinding.ActivityMyProfileBinding
import com.htf.diva.models.AboutModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.view.*
import kotlinx.android.synthetic.main.dialog_pick_image.view.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MyProfileActivity : BaseDarkActivity<ActivityMyProfileBinding, ProfileViewModel>(
    ProfileViewModel::class.java),
    View.OnClickListener {
    private var currActivity: Activity =this
    private var userProfile=AboutModel()
    private val REQUEST_CODE_UPDATE_PROFILE=1111
    private var file: File? = null
    private var uri: Uri?=null
    private var REQUEST_CAMERA = 433
    private var RESULT_LOAD = 423
    private var part: MultipartBody.Part? = null


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,MyProfileActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_my_profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=currActivity.getString(R.string.my_profile)
        binding.profileViewModel = viewModel
        setListener()
        viewModelInitialize()
        viewModel.profileDetails()
    }

    private fun setListener() {
        btnEditProfile.setOnClickListener(this)
        ivCamera.setOnClickListener(this)
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mProfileDetailResponse, this::onHandleProfileResponse)
    }

    @SuppressLint("SetTextI18n")
    private fun onHandleProfileResponse(appUserProfile: AboutModel?){
        appUserProfile?.let {
            userProfile=appUserProfile
            tvUserName.text=appUserProfile.name
            tvUserMobile.text="+"+appUserProfile.dialCode+""+appUserProfile.mobile
            tvAge.text=appUserProfile.age+" "+currActivity.getString(R.string.years)
            tvHeight.text=appUserProfile.height+" "+currActivity.getString(R.string.feets)
            tvWeight.text=appUserProfile.weight+" "+currActivity.getString(R.string.kg)
            Glide.with(currActivity).load(Constants.Urls.USER_IMAGE_URL + appUserProfile.profileImage).centerCrop()
                .placeholder(R.drawable.user).into(ivUser)

            val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
            currUser!!.user!!.profileImage=appUserProfile.profileImage
            AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(currUser)


        }
    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnEditProfile->{
                EditProfileActivity.open(currActivity,userProfile)
            }

            R.id.ivCamera->{
                if (PermissionUtility.checkCameraPermission(currActivity))
                    pickImageDialog()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_UPDATE_PROFILE->{
                if (data!=null && resultCode==Activity.RESULT_OK){
                    val appUserProfile=data.getSerializableExtra("userProfileData") as AboutModel
                    tvUserName.text=appUserProfile.name
                    tvUserMobile.text="+"+appUserProfile.dialCode+""+appUserProfile.mobile
                    tvAge.text=appUserProfile.age+" "+currActivity.getString(R.string.years)
                    tvHeight.text=appUserProfile.height+" "+currActivity.getString(R.string.cm)
                    tvWeight.text=appUserProfile.weight+" "+currActivity.getString(R.string.kg)
                    val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
                    currUser!!.user!!.profileImage=appUserProfile.profileImage
                    AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(currUser)

                }
            }
            REQUEST_CAMERA->{
                if (resultCode==Activity.RESULT_OK ){
                    val destination=
                        File(currActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"Diva${System.currentTimeMillis()}.jpg")
                    UCrop.of(uri!!, Uri.fromFile(destination))
                        .withAspectRatio(4F, 3F)
                        .withMaxResultSize(1024, 1024)
                        .start(currActivity)
                }
            }

            RESULT_LOAD->{
                if (resultCode==Activity.RESULT_OK && data!=null){
                    val filePath = data.data
                    val destination=
                        File(currActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"Diva${System.currentTimeMillis()}.jpg")
                    UCrop.of(filePath!!, Uri.fromFile(destination))
                        .withAspectRatio(4F, 3F)
                        .withMaxResultSize(1024, 1024)
                        .start(currActivity)

                }
            }

            UCrop.REQUEST_CROP->{
                if (resultCode==Activity.RESULT_OK && data!=null){
                    val resultUri=UCrop.getOutput(data)
                    uri=null
                    val file=File(resultUri?.path!!)
                    Picasso.get().load(file).into(binding.root.ivUser)
                    val mFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    part = MultipartBody.Part.createFormData("profile_image", file.name, mFile)
                    if (part != null) {
                        viewModel.onUpdateProfileImage(part!!)
                    }
                }
            }


        }
    }

    private fun pickImageDialog() {
        val builder = Dialog(currActivity,R.style.DialogOtp)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_pick_image, null)
        builder.setContentView(dialogLangView)
        builder.setCancelable(true)

        dialogLangView.llCamera.setOnClickListener {
            builder.dismiss()
            takePicture()
        }

        dialogLangView.llGallery.setOnClickListener {
            builder.dismiss()
            galleryIntent()
        }

        dialogLangView.ivCloses.setOnClickListener {
            builder.dismiss()
        }

        builder.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        builder.window!!.setGravity(Gravity.BOTTOM)
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.show()

    }

    private fun takePicture() {
        try {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                uri = outputMediaFileUri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                startActivityForResult(intent, REQUEST_CAMERA)
            }else{
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val capturedFile = outputMediaFile
                uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(
                        currActivity,
                        currActivity.applicationContext.packageName + ".fileProvider",
                        capturedFile!!
                    )
                } else {
                    Uri.fromFile(capturedFile)
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                startActivityForResult(intent, REQUEST_CAMERA)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private val outputMediaFile: File?
        get() {
            var mediaStorageDir: File?=null
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
                mediaStorageDir= File(
                    currActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS+"/Media"),
                    "Images"
                )
            }else{
                mediaStorageDir= File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    getString(R.string.app_name)
                )
            }
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
        }



    private val outputMediaFileUri: Uri?
        get() {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName= "IMG_$timeStamp.jpg"
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME,fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Diva/")
                put(MediaStore.Images.Media.IS_PENDING, 0)
            }
            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            return currActivity.contentResolver?.insert(collection, values)
        }

    private fun galleryIntent() {
        try {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, RESULT_LOAD)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



}