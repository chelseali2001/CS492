package com.example.cs492finalproject

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageAnalysis
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


class CreatePostFragment : Fragment(R.layout.fragment_create_post){
    private val subscriptionKey = "06f4fdb305224a3e884f7f6a2cac88d9"
    private val endpoint = "https://interiordesignapp.cognitiveservices.azure.com/"
    private lateinit var compVisClient: ComputerVisionClient
    private lateinit var BSelectImage : Button
    private lateinit var IVPreviewImage : ImageView
    private lateinit var textTag : TextView
    private lateinit var inputTag: EditText
    private lateinit var textDesc: TextView
    private lateinit var inputDesc: EditText
    private lateinit var textColor: TextView
    private lateinit var inputColor: EditText
    private lateinit var textLocation: TextView
    private lateinit var inputLocation: EditText
    private lateinit var post_button: Button
    private lateinit var takePhoto: Button
    private lateinit var thumbnail: Bitmap
    private lateinit var colorText: TextView
    private lateinit var colorInput: EditText
    private lateinit var locationText: TextView
    private lateinit var locationInput: EditText
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var savePost: Room
    private var imageUri: Uri? = null
    private val pickImage = 100

    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compVisClient = ComputerVisionManager.authenticate(subscriptionKey).withEndpoint(endpoint)

        BSelectImage = view.findViewById(R.id.BSelectImage)
        IVPreviewImage = view.findViewById(R.id.IVPreviewImage)
        post_button = view.findViewById(R.id.post_button)
        takePhoto = view.findViewById(R.id.TakePhoto)
        textTag = view.findViewById(R.id.tag_text)
        inputTag = requireView().findViewById(R.id.tag_input)
        textDesc = requireView().findViewById(R.id.description_text)
        inputDesc = requireView().findViewById(R.id.description_input)
        colorText = requireView().findViewById(R.id.color_text)
        colorInput = requireView().findViewById(R.id.color_input)
        locationText = requireView().findViewById(R.id.location_text)
        locationInput = requireView().findViewById(R.id.location_input)
        loadingIndicator = requireView().findViewById(R.id.loading_indicator)
        BSelectImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        takePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_PERMISSION_CODE)
        }
        post_button.setOnClickListener {
            if (inputTag.text.toString().isEmpty() ||  inputDesc.text.toString().isEmpty() || colorInput.text.toString().isEmpty() || locationInput.text.toString().isEmpty()) {
                Snackbar.make(requireView(), "Please enter values for all fields.", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(requireView(), "Posted.", Snackbar.LENGTH_SHORT).show()
                IVPreviewImage.setImageResource(0)
                inputTag.text.clear()
                inputDesc.text.clear()
                colorInput.text.clear()
                locationInput.text.clear()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data!!.data != null) {
            loadingIndicator.visibility = View.VISIBLE
            BSelectImage.visibility = View.INVISIBLE
            IVPreviewImage.visibility = View.INVISIBLE
            post_button.visibility = View.INVISIBLE
            takePhoto.visibility = View.INVISIBLE
            textTag.visibility = View.INVISIBLE
            inputTag.visibility = View.INVISIBLE
            textDesc.visibility = View.INVISIBLE
            inputDesc.visibility = View.INVISIBLE
            colorText.visibility = View.INVISIBLE
            colorInput.visibility = View.INVISIBLE
            locationText.visibility = View.INVISIBLE
            locationInput.visibility = View.INVISIBLE

            if (resultCode == RESULT_OK && requestCode == pickImage) {
                imageUri = data?.data
                IVPreviewImage.setImageURI(imageUri)
            }
            if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
                thumbnail = data!!.extras!!.get("data") as Bitmap
                val bytes = ByteArrayOutputStream()
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                val path = MediaStore.Images.Media.insertImage(
                    requireContext().contentResolver,
                    thumbnail,
                    "Title",
                    null
                )
                imageUri = Uri.parse(path.toString())
                IVPreviewImage.setImageURI(imageUri)
            }

            val bitmap = (IVPreviewImage.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageByteArray: ByteArray = baos.toByteArray()

            val featuresToExtractFromLocalImage: MutableList<VisualFeatureTypes> = ArrayList()
            featuresToExtractFromLocalImage.add(VisualFeatureTypes.TAGS)
            GlobalScope.launch(Dispatchers.IO) {
                val analysis: ImageAnalysis = compVisClient.computerVision().analyzeImageInStream().withImage(imageByteArray).withVisualFeatures(featuresToExtractFromLocalImage).execute()
                var tags = ""
                for (tag in analysis.tags()) {
                    tags += tag.name() + ' '
                }
                withContext(Dispatchers.Main) {
                    loadingIndicator.visibility = View.INVISIBLE
                    BSelectImage.visibility = View.VISIBLE
                    IVPreviewImage.visibility = View.VISIBLE
                    post_button.visibility = View.VISIBLE
                    takePhoto.visibility = View.VISIBLE
                    textTag.visibility = View.VISIBLE
                    inputTag.visibility = View.VISIBLE
                    textDesc.visibility = View.VISIBLE
                    inputDesc.visibility = View.VISIBLE
                    colorText.visibility = View.VISIBLE
                    colorInput.visibility = View.VISIBLE
                    locationText.visibility = View.VISIBLE
                    locationInput.visibility = View.VISIBLE
                    inputTag.setText(tags)
                }
            }
        }
    }
}