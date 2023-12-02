package br.com.simon.environmentthreatsfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class EditEnvironmentThreat : AppCompatActivity() {

    private lateinit var db: EnvironmentThreatSQLiteDatabase
    private lateinit var currentEnvironmentThreat: EnvironmentThreat

    private lateinit var addressText: EditText
    private lateinit var dateText: EditText
    private lateinit var descriptionText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_environment_threat)

        db = EnvironmentThreatSQLiteDatabase(baseContext)

        val id = intent.getLongExtra("ID", 0)

        addressText = findViewById(R.id.editAddressText)
        dateText = findViewById(R.id.editDateText)
        descriptionText = findViewById(R.id.editDescriptionText)

        currentEnvironmentThreat = db.getEnvironmentThreat(id).get()

        addressText.setText(currentEnvironmentThreat.address)
        dateText.setText(currentEnvironmentThreat.date)
        descriptionText.setText(currentEnvironmentThreat.description)

    }

    fun updateEnvironmentThreat(v: View) {
        db.updateEnvironmentThreat(
            EnvironmentThreat(currentEnvironmentThreat.id,
                                                     addressText.text.toString(),
                                                     dateText.text.toString(),
                                                     descriptionText.text.toString())
        )
        finish()
    }


}