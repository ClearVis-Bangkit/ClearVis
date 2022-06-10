package com.dicoding.bintangpr.clearvis.view.eyecheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import com.canhub.cropper.CropImage
import com.dicoding.bintangpr.clearvis.databinding.FragmentEyeCheckBinding

class EyeCheckFragment : Fragment() {
    private var _binding: FragmentEyeCheckBinding? = null
    private val binding get() = _binding

    private val cropActivityResultContract =
        object : ActivityResultContract<Any?, CropImage.ActivityResult?>() {
            override fun createIntent(context: Context, input: Any?): Intent {
                return CropImage.activity()
                    .getIntent(activity!!)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): CropImage.ActivityResult? {
                return CropImage.getActivityResult(intent)
            }
        }
    private lateinit var cropActivityResultLauncer: ActivityResultLauncher<Any?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEyeCheckBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cropActivityResultLauncer = registerForActivityResult(cropActivityResultContract) {
            it?.let { data ->
                goToResultActivity(data)
            }
        }

        binding?.btnPicture?.setOnClickListener {
            cropActivityResultLauncer.launch(null)
        }
    }

    private fun goToResultActivity(imageResult: CropImage.ActivityResult) {
        val intent = Intent(activity, ResultActivity::class.java)
        intent.putExtra(ResultActivity.IMAGE_DATA, imageResult)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}