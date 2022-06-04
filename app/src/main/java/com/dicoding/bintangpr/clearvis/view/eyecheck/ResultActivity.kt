package com.dicoding.bintangpr.clearvis.view.eyecheck

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import arrow.core.Either
import arrow.core.getOrElse
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImage
import com.dicoding.bintangpr.clearvis.databinding.ActivityResultBinding
import io.github.nefilim.kjwt.JWT
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val eyeCheckViewModel: EyeCheckViewModel by viewModel()
    private val mInputSize = 224
    private val mModelPath = "model_VGGBased-3_multiclass.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: Classifier
    private lateinit var detectionResult: List<Classifier.Recognition>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        val extras = intent.extras
        extras?.let {
            val imageResult = extras.getParcelable<CropImage.ActivityResult>(IMAGE_DATA)
            val file = File(imageResult?.getUriFilePath(this@ResultActivity)!!)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            Glide.with(this@ResultActivity)
                .load(bitmap)
                .into(binding.ivEye)
            detectImage(bitmap)

            eyeCheckViewModel.isLoading.observe(this) {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }

            eyeCheckViewModel.getUser().observe(this) { data ->
                val getId = JWT.decode(data.accessToken).map {
                    it.claimValue("id").getOrElse { "error" }
                }
                val id = when (getId) {
                    is Either.Right -> getId.value
                    else -> "error"
                }

                val userId = id.toRequestBody("text/plain".toMediaType())
                val status = detectionResult[0].title.toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image",
                    file.name + "jpg",
                    requestImageFile
                )

                eyeCheckViewModel.uploadHistory(data.accessToken, userId, imageMultipart, status)
            }
        }

        binding.btnCheckAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity, EyeCheckFragment::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun detectImage(bitmap: Bitmap) {
        detectionResult = classifier.recognizeImage(bitmap)
        val confidence = detectionResult[0].confidence * 100
        binding.tvResult.text = detectionResult[0].title
        binding.tvConfidence.text = confidence.toString()
    }

    companion object {
        const val IMAGE_DATA = "image_data"
    }
}