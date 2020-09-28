package com.joseluisgs.helloandroidkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // creamos como propiedades si vamos a usarlos en toda la clase
    private var nombre: String = ""
    private var correo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Ciclo de vida.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Le asignamos la actividad (su layout)

        // Primer paso recuperar los componentes que vayamos a utilizar enlazando lógica y vistas
        val modulo = getString(R.string.modulo) // Nos ahorramos poner el tipo porque lo definimos en compilación

        // Añadimos los eventos a los componentes
        mainFloSaludo.setOnClickListener { ejemploSnackBar(it, modulo) }
        mainBtnAccion.setOnClickListener {
            // podemos hacer las acciones entre llaves, pero no es recomendable
            this.nombre = mainEditNombre.text.toString()
            ejemploToast("Hola te llamas: ${this.nombre}");
        }
        mainBtnMostrarDatos.setOnClickListener { abrirMostrarDatos() }

    }

    // Abrimos otra actividad
    private fun abrirMostrarDatos() {
        // Tomamos los datos
        val nombre = mainEditNombre.text.toString()
        val correo = mainEditCorreo.text.toString()
        // llamamos al intent
        // https://developer.android.com/guide/components/intents-filters?hl=es-419
        val intent = Intent(this, MostrarDatosActivity::class.java).apply {
            // Adjuntamos los parametros, clave valor
            putExtra("NOMBRE", nombre)
            putExtra("CORREO", correo)
        }
        // Comenzamos la actividad
        startActivity(intent)
    }

    // Función que saluda usando un SnackBar
    // https://developer.android.com/reference/com/google/android/material/snackbar/Snackbar
    private fun ejemploSnackBar(view: View, modulo: String) {
        Snackbar.make(view, "Estás en el módulo: $modulo", Snackbar.LENGTH_LONG)
            // Esto es la acción
            .setAction("Ver") {
                // Método asicado a la acción
                ejemploToast("¡Hola otra vez! $nombre")
            }
            .show()
    }

    // Ejemplo de mensaje Toast
    // https://developer.android.com/reference/android/widget/Toast
    private fun ejemploToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    // Creación del menú
    // https://developer.android.com/guide/topics/ui/menus?hl=es#kotlin
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_acerca_de -> {
                menuAcercaDe()
                true
            }
            R.id.menu_otra_opcion -> {
                menuOtraOpcion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun menuOtraOpcion() {
        // Escribimos en el log
        // https://developer.android.com/studio/debug/am-logcat?hl=es
        Log.i("ETIQUETA", "Mensaje de log")
        Toast.makeText(this, "Has pulsado Otra Opción", Toast.LENGTH_SHORT).show()
    }

    private fun menuAcercaDe() {
        val intent = Intent(this, AcercaDeActivity::class.java)
        startActivity(intent)
    }

    // Sobre el ciclo de vida
    // https://developer.android.com/guide/components/activities/activity-lifecycle?hl=es
    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
        super.onPause()
    }

    override fun onStop() {
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}