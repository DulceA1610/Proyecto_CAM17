package com.example.cam17

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContent(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.menu_home

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    true
                }
                R.id.menu_cart -> {
                    val intent = Intent(this, CarritoActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.menu_profile -> {
                    val intent = Intent(this, PerfilActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                else -> false
            }
        }


        val btnReposteria = findViewById<ImageButton>(R.id.btnReposteria)
        val btnPintura = findViewById<ImageButton>(R.id.btnPintura)
        val btnPinata = findViewById<ImageButton>(R.id.bntPinata)

        btnReposteria.setOnClickListener {
            val intent = Intent(this, ReposteriaActivity::class.java)
            startActivity(intent)
        }

        btnPintura.setOnClickListener {
            val intent = Intent(this, PinturaActivity::class.java)
            startActivity(intent)
        }

        btnPinata.setOnClickListener {
            val intent = Intent(this, PinataActivity::class.java)
            startActivity(intent)
        }
        val alumnos: Button=findViewById(R.id.alumnos)
        alumnos.setOnClickListener {
            val intent = Intent(this, Alumnos::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.menu_home
    }
}
