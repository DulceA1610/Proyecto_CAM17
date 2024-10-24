 package com.example.cam17

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

 open class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        var id_btnregistrase:Button=findViewById(R.id.id_btnregistrase)
        id_btnregistrase.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }
        var id_btnacceder:Button=findViewById(R.id.id_acceder)
        id_btnacceder.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        var id_btnolvcontra: Button=findViewById(R.id.id_btnolvidocontra)
        id_btnolvcontra.setOnClickListener {
            val intent=Intent(this, Recuperar_contrase::class.java)
            startActivity(intent)
        }
    }
}