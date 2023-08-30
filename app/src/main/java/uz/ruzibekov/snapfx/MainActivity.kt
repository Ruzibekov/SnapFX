package uz.ruzibekov.snapfx

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Mode
import com.otaliastudios.cameraview.filter.Filters
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private var cameraView: CameraView? = null
    private var btnTakePhoto: Button? = null
    private var filterGray: View? = null
    private var filter2: View? = null
    private var filter3: View? = null
    private var filter4: View? = null
    private var filter5: View? = null
    private var filter6: View? = null
    private var filter7: View? = null
    private var filter8: View? = null
    private var filter9: View? = null
    private var filter10: View? = null
    private var filter11: View? = null
    private var filter12: View? = null
    private var filter13: View? = null
    private var filter14: View? = null
    private var filter15: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterGray = findViewById(R.id.filter_gray)
        filter2 = findViewById(R.id.filter_2)
        filter3 = findViewById(R.id.filter_3)
        filter4 = findViewById(R.id.filter_4)
        filter5 = findViewById(R.id.filter_5)
        filter6 = findViewById(R.id.filter_6)
        filter7 = findViewById(R.id.filter_7)
        filter8 = findViewById(R.id.filter_8)
        filter9 = findViewById(R.id.filter_9)
        filter10 = findViewById(R.id.filter_10)
        filter11 = findViewById(R.id.filter_11)
        filter12 = findViewById(R.id.filter_12)
        filter13 = findViewById(R.id.filter_13)
        filter14 = findViewById(R.id.filter_14)
        filter15 = findViewById(R.id.filter_15)

        cameraView = findViewById(R.id.camera_view)
        cameraView?.mode = Mode.PICTURE
        btnTakePhoto = findViewById(R.id.take_photo)

        cameraView?.setLifecycleOwner(this)

        cameraView?.addCameraListener(object : CameraListener() {

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onPictureTaken(result: PictureResult) {

                // Convert the image to a Bitmap
                result.toBitmap {
                    if (it != null) {
                        saveBitmapToGallery(it)
                    }
                }
            }
        })

        btnTakePhoto?.setOnClickListener {
            cameraView?.takePictureSnapshot()
        }

        filterGray?.setOnClickListener {
            cameraView?.filter = Filters.BLACK_AND_WHITE.newInstance();
        }

        filter2?.setOnClickListener {
            cameraView?.filter = Filters.CONTRAST.newInstance();
        }

        filter3?.setOnClickListener {
            cameraView?.filter = Filters.BRIGHTNESS.newInstance();
        }

        filter4?.setOnClickListener {
            cameraView?.filter = Filters.AUTO_FIX.newInstance();
        }

        filter5?.setOnClickListener {
            cameraView?.filter = Filters.CROSS_PROCESS.newInstance();
        }

        filter6?.setOnClickListener {
            cameraView?.filter = Filters.DOCUMENTARY.newInstance();
        }

        filter7?.setOnClickListener {
            cameraView?.filter = Filters.DUOTONE.newInstance();
        }

        filter8?.setOnClickListener {
            cameraView?.filter = Filters.FILL_LIGHT.newInstance();
        }

        filter9?.setOnClickListener {
            cameraView?.filter = Filters.GAMMA.newInstance();
        }

        filter10?.setOnClickListener {
            cameraView?.filter = Filters.GRAIN.newInstance();
        }

        filter11?.setOnClickListener {
            cameraView?.filter = Filters.GRAYSCALE.newInstance();
        }

        filter12?.setOnClickListener {
            cameraView?.filter = Filters.HUE.newInstance();
        }

        filter13?.setOnClickListener {
            cameraView?.filter = Filters.INVERT_COLORS.newInstance();
        }

        filter14?.setOnClickListener {
            cameraView?.filter = Filters.LOMOISH.newInstance();
        }

        filter15?.setOnClickListener {
            cameraView?.filter = Filters.TEMPERATURE.newInstance();
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val displayName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val contentResolver: ContentResolver = applicationContext.contentResolver

        // Insert the image into the MediaStore
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            imageUri?.let { uri ->
                val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                outputStream?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // Adjust quality as needed
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle any errors that may occur during the save process
        }
    }

}
