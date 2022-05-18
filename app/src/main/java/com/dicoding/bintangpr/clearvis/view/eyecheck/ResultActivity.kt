package com.dicoding.bintangpr.clearvis.view.eyecheck

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImage
import com.dicoding.bintangpr.clearvis.databinding.ActivityResultBinding
import com.dicoding.bintangpr.clearvis.view.main.MainActivity
import java.io.ByteArrayOutputStream
import java.io.File

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val mInputSize = 224
    private val mModelPath = "model_VGGBased-3_multiclass.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: Classifier

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
        }

        binding.btnCheckAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun detectImage(bitmap: Bitmap) {
        val detectionResult = classifier.recognizeImage(bitmap)
        val confidence = detectionResult[0].confidence * 100
        binding.tvResult.text = detectionResult[0].title
        binding.tvConfidence.text = confidence.toString()
    }

    companion object {
        const val IMAGE_DATA = "image_data"
    }
}