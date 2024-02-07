package com.app.wocare

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.wocare.ml.TfLiteModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private var imageCapture: ImageCapture? = null
    private var imageSize: Int = 300
    private lateinit var foto: PreviewView
    private lateinit var btnOk: Button
    private lateinit var preview: ImageView
    private lateinit var btnGallery: FloatingActionButton
    private lateinit var resultLauncherGallery: ActivityResultLauncher<Intent>

    override fun onStart() {
        super.onStart()
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_camera)
        dialog.setCancelable(false)
        dialogFull(dialog)

        btnOk = dialog.findViewById(R.id.okay)
        btnOk.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun dialogFull(d: Dialog) {
        d.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_camera, container, false)
        foto = v.findViewById(R.id.viewFinder)
        btnGallery = v.findViewById(R.id.gallery)
        preview = v.findViewById(R.id.iv)

        btnGallery.setOnClickListener{
            galleryChooser()
        }

        resultLauncherGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                val dataFoto = it.data
                if (dataFoto != null){
                    val uriFoto: Uri? = dataFoto.data
                    val source = ImageDecoder.createSource(requireContext().contentResolver, uriFoto!!)
                    var bitmapFoto: Bitmap = ImageDecoder.decodeBitmap(source)
                    val dimension = Math.min(bitmapFoto.width, bitmapFoto.height)
                    bitmapFoto = ThumbnailUtils.extractThumbnail(bitmapFoto, dimension, dimension)
                    bitmapFoto = Bitmap.createScaledBitmap(bitmapFoto, imageSize, imageSize, false )
                    classifyFoto(bitmapFoto)
                }
            } else {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
        }

        if (allPermissionGranted()){
            statCamera()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, 20)
        }
        return v
    }

    private fun classifyFoto(foto: Bitmap) {
        val model = TfLiteModel.newInstance(requireContext())

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 300, 300, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        inputFeature0.loadBuffer(byteBuffer)
        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        // Releases model resources if no longer used.
        model.close()
    }

    private fun galleryChooser() {
        val i = Intent(Intent.ACTION_PICK)
        i.setType("image/*")
        resultLauncherGallery.launch(i)
    }

    private fun statCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener(kotlinx.coroutines.Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(foto.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    requireActivity(), cameraSelector, preview, imageCapture
                )

            } catch (e: Exception){
                Toast.makeText(requireContext(), "Use Case Binding Failed$e", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
}

