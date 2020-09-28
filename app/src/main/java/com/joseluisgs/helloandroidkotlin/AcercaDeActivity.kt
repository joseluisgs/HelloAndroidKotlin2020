package com.joseluisgs.helloandroidkotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_acerca_de.*


class AcercaDeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)

        // Eventos
        acercaBtnTwitter.setOnClickListener { abrirURL("https://twitter.com/joseluisgs") }
        acercaBtnGitHub.setOnClickListener { abrirURL("https://github.com/joseluisgs") }
        acercaBtnCorreo.setOnClickListener { mandarCorreo("pepe@pepe.com", "Prueba", "Texto de prueba") }
    }

    // Algunos intent que debemos conocer
    // https://developer.android.com/guide/components/intents-common?hl=es-419

    // Abrir una URL
    private fun abrirURL(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(intent)
    }

    // Mandar un email
    private fun mandarCorreo(receptor: String, asunto: String, mensaje: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        // Los receptores deben ser un array, ya sean uno o varios, por eso los casteamos
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(receptor))
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)
        try {
            startActivity(Intent.createChooser(intent, "Enviar usando..."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }
}