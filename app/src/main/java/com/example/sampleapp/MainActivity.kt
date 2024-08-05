package com.example.sampleapp

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btn = findViewById<Button>(R.id.btn_startService)
        btn.setOnClickListener {
            val orgId = findViewById<EditText>(R.id.orgId).text.toString()
            val userId = findViewById<EditText>(R.id.userId).text.toString()

            if (orgId.isBlank() && userId.isBlank()) {
                Toast.makeText(this, "Please enter ids", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Intent().also {
                it.component = ComponentName(
                    "ma.dista",
                    "ma.dista.activities.startServiceActivity.StartServiceActivity"
                )

                // These flags ensure that the activity starts fresh, with no back stack
                it.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                it.putExtra("clientId", orgId)
                it.putExtra("userId", userId)

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startActivity(it)
                    } else {
                        startActivity(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}