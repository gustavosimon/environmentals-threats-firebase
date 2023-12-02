package br.com.simon.environmentthreatsfirebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EnvironmentThreatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = findViewById(R.id.listView)
        val db = EnvironmentThreatSQLiteDatabase(baseContext)
        adapter = EnvironmentThreatsAdapter(db, baseContext)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, _, l ->
            changeToUpdate(l)
        }

        listView.setOnItemLongClickListener { _, _, _, l ->
            db.removeEnvironmentThreat(l.toInt())
            adapter.notifyDataSetChanged()
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

    private fun changeToUpdate(id: Long) {
        val it = Intent(baseContext, EditEnvironmentThreat::class.java)
        it.putExtra("ID", id)
        startActivity(it)
    }

}

class EnvironmentThreatsAdapter(private val db: EnvironmentThreatSQLiteDatabase,
                                context: Context) : BaseAdapter() {

    private val inflator = LayoutInflater.from(context)

    override fun getCount(): Int {
        return db.getEnvironmentThreats().size
    }

    override fun getItem(i: Int): Any {
        return db.getEnvironmentThreats()[i]
    }

    override fun getItemId(i: Int): Long {
        return db.getEnvironmentThreats()[i].id
    }

    @SuppressLint("ViewHolder")
    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        val v: View = inflator.inflate(R.layout.environment_threat, null)
        val txtSaida = v.findViewById<View>(R.id.description) as TextView
        val txtDate = v.findViewById<View>(R.id.date) as TextView
        val environmentThreat: EnvironmentThreat = db.getEnvironmentThreats()[i]
        txtSaida.text = environmentThreat.description
        txtDate.text = environmentThreat.date
        return v
    }

}