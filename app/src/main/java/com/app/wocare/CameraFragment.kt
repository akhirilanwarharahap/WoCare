package com.app.wocare

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

class CameraFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private var imageCapture: ImageCapture? = null
    lateinit var foto: PreviewView
    lateinit var btnOk: Button

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

        if (allPermissionGranted()){
            statCamera()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, 20)
        }

        return v
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

