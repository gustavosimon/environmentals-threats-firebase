package br.com.simon.environmentthreatsfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class AddEnvironmentThreat : AppCompatActivity() {

    private lateinit var db: EnvironmentThreatSQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_environment_threat)
        db = EnvironmentThreatSQLiteDatabase(baseContext)
    }

    fun addThreat(v: View) {
        val addressText: EditText = findViewById(R.id.addAddressText)
        val dateText: EditText = findViewById(R.id.addDateText)
        val descriptionText: EditText = findViewById(R.id.addDescriptionText)
        val environmentThreat = EnvironmentThreat(0, addressText.text.toString(), dateText.text.toString(), descriptionText.text.toString())
        db.addEnvironmentThreat(environmentThreat)
        finish()
    }

}