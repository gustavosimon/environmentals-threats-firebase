package br.com.simon.environmentthreatsfirebase

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import android.util.Base64
import java.util.Optional

class AddEnvironmentThreat : AppCompatActivity() {

    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val root: DatabaseReference = db.reference
    private val threats: DatabaseReference = root.child(Constants.THREATS_KEY)

    private var bmp: Optional<Bitmap> = Optional.empty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_environment_threat)
    }

    fun addThreat(v: View) {
        val addressText: EditText = findViewById(R.id.addAddressText)
        val dateText: EditText = findViewById(R.id.addDateText)
        val descriptionText: EditText = findViewById(R.id.addDescriptionText)

        val image: String = loadImage()

        val environmentThreat = EnvironmentThreat(addressText.text.toString(), dateText.text.toString(), descriptionText.text.toString(), image)
        val key = threats.push().key!!
        threats.child(key).setValue(environmentThreat)
        finish()
    }

    private fun loadImage(): String {
        if (!bmp.isPresent) {
            return ""
        }
        val baos = ByteArrayOutputStream()
        bmp.ifPresent { x -> x.compress(Bitmap.CompressFormat.JPEG, 100, baos) }
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    fun takePicture(v: View) {
        val camera: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, Constants.CAMERA_CALL)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.CAMERA_CALL && resultCode == RESULT_OK) {
            bmp = Optional.of(data!!.extras!!.get("data") as Bitmap)
            val image: ImageView = findViewById(R.id.imageView)
            bmp.ifPresent { x -> image.setImageBitmap(x) }
        }
    }

}