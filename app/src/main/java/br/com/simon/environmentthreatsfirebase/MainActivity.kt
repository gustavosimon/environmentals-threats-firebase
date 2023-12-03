package br.com.simon.environmentthreatsfirebase

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val root: DatabaseReference = db.reference
    private val threats: DatabaseReference = root.child(Constants.THREATS_KEY)

    private lateinit var adapter: FirebaseListAdapter<EnvironmentThreat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = findViewById(R.id.listView)
        val db = EnvironmentThreatSQLiteDatabase(baseContext)

        val options = FirebaseListOptions.Builder<EnvironmentThreat>()
                                         .setQuery(threats, EnvironmentThreat::class.java)
                                         .setLayout(R.layout.environment_threat)
                                         .build()

        adapter = object: FirebaseListAdapter<EnvironmentThreat>(options) {

            override fun populateView(v: View, threat: EnvironmentThreat, position: Int) {
                val txtSaida = v.findViewById<View>(R.id.description) as TextView
                val txtDate = v.findViewById<View>(R.id.date) as TextView
                val image = v.findViewById<View>(R.id.image) as ImageView
                if (threat.image != null) {
                    val baos: ByteArray = Base64.decode(threat.image, Base64.DEFAULT)
                    val bmp: Bitmap = BitmapFactory.decodeByteArray(baos, 0, baos.size)
                    image.setImageBitmap(bmp)
                }
                txtSaida.text = threat.description
                txtDate.text = threat.date
            }

        }

        listView.adapter = adapter

        adapter.startListening()

        listView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getRef(position)
            changeToUpdate(item.key!!, adapter.getItem(position))
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val item = adapter.getRef(position)
            item.removeValue()
            true
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    fun addThreat(v: View) {
        val it = Intent(baseContext, AddEnvironmentThreat::class.java)
        startActivity(it)
    }

    private fun changeToUpdate(key: String, threat: EnvironmentThreat) {
        val it = Intent(baseContext, EditEnvironmentThreat::class.java)
        it.putExtra("KEY", key)
        it.putExtra("THT", threat)
        startActivity(it)
    }

}

