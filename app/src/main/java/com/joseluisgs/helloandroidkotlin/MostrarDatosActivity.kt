package com.joseluisgs.helloandroidkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mostrar_datos.*

class MostrarDatosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_datos)

        // Obtenemos los datos que nos llega del padre
        val nombre = intent.getStringExtra("NOMBRE")
        val correo = intent.getStringExtra("CORREO")

        // Modificamos la interfaz con los datos (podría hacerlo de un paso, pero mejor ahora ir poco a poco)
        mostrarTextNombre.text = nombre;
        mostrarTextCorreo.text = correo;
    }
}