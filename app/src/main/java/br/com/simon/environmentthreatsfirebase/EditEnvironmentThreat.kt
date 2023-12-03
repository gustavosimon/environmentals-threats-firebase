package br.com.simon.environmentthreatsfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditEnvironmentThreat : AppCompatActivity() {

    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val root: DatabaseReference = db.reference
    private val threats: DatabaseReference = root.child(Constants.THREATS_KEY)

    private lateinit var currentThreat: EnvironmentThreat

    private lateinit var addressText: EditText
    private lateinit var dateText: EditText
    private lateinit var descriptionText: EditText

    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_environment_threat)

        addressText = findViewById(R.id.editAddressText)
        dateText = findViewById(R.id.editDateText)
        descriptionText = findViewById(R.id.editDescriptionText)

        key = intent.getStringExtra("KEY")!!
        currentThreat = intent.getSerializableExtra("THT") as EnvironmentThreat

        addressText.setText(currentThreat.address)
        dateText.setText(currentThreat.date)
        descriptionText.setText(currentThreat.description)
    }

    fun updateEnvironmentThreat(v: View) {
        val newThreat = EnvironmentThreat(addressText.text.toString(),
                                          dateText.text.toString(),
                                          descriptionText.text.toString(),
                                          currentThreat.image)
        threats.child(key).setValue(newThreat);
        finish()
    }

}