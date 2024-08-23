package com.example.sampleapp

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.Key
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPublicKey
import javax.crypto.Cipher

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


        val assetManager = this.assets
        val fileNames = assetManager.list("")
        fileNames?.forEach {
            println("File in assets: $it")
        }

        val publicKey = readX509PublicKey()

        val btn = findViewById<Button>(R.id.btn_startService)
        btn.setOnClickListener {
            val orgId = findViewById<EditText>(R.id.orgId).text.toString()
            val userId = findViewById<EditText>(R.id.userId).text.toString()

            if (orgId.isBlank() && userId.isBlank()) {
                return@setOnClickListener
            }

            Intent().also {
                it.component = ComponentName(
                    "dista.suryoday",
                    "ma.dista.activities.startServiceActivity.StartServiceActivity"
                )

                val orgIdByteArray = encryptData(publicKey , orgId.toByteArray(Charsets.UTF_8))
                val userIdByteArray = encryptData(publicKey , userId.toByteArray(Charsets.UTF_8))

                // These flags ensure that the activity starts fresh, with no back stack
                it.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_CLEAR_TOP
                it.putExtra("clientId", orgId)
                it.putExtra("userId", userIdByteArray)
                it.putExtra("SHA1","995F36DAACAB23AE3F7824864A97E6AE5908A302")

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

    private fun encryptData(key: Key, data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE,key)
        return cipher.doFinal(data)
    }

    private fun readX509PublicKey(): RSAPublicKey {

        val key = """
       -----BEGIN CERTIFICATE-----
       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
       -----END CERTIFICATE-----
    """.trimIndent()

        // Clean the PEM file content to remove headers and footers
        val certificatePEM = key
            .replace("-----BEGIN CERTIFICATE-----", "")
            .replace("-----END CERTIFICATE-----", "")
            .replace("\n", "")
            .replace("\r", "")

        // Decode Base64
        val encoded = Base64.decode(certificatePEM, Base64.DEFAULT)

        // Create CertificateFactory instance
        val certificateFactory = CertificateFactory.getInstance("X.509")

        // Generate the certificate from the decoded data
        val certificate = certificateFactory.generateCertificate(encoded.inputStream()) as X509Certificate

        // Extract the public key from the certificate
        return certificate.publicKey as RSAPublicKey
    }
}