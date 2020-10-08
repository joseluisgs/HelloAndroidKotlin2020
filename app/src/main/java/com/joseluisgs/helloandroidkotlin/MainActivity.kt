package com.joseluisgs.helloandroidkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    // creamos como propiedades si vamos a usarlos en toda la clase
    private var nombre: String = ""
    private var currentScore = 1
    private var currentLevel = 1
    private var colors = arrayOfNulls<String>(0)


    /**
     * Función para costruir la Actividad
     * @param savedInstanceState Bundle? Bundle con el que salvamos las cosas
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Ciclo de vida. Nada mas crear salvamos el estado
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Le asignamos la actividad (su layout)

        // Primer paso recuperar los componentes que vayamos a utilizar enlazando lógica y vistas
        val modulo = getString(R.string.modulo) // Nos ahorramos poner el tipo porque lo definimos en compilación

        // Añadimos los eventos a los componentes
        mainFloSaludo.setOnClickListener { ejemploSnackBar(it, modulo) }
        mainBtnAccion.setOnClickListener {
            // podemos hacer las acciones entre llaves, pero no es recomendable. Esto es una función lambda
            this.nombre = mainEditNombre.text.toString()
            ejemploToast("Hola te llamas: ${this.nombre}")
        }
        mainBtnMostrarDatos.setOnClickListener { abrirMostrarDatos() }
        mainBtnColor.setOnClickListener { cargarColores() }
    }

    // Carga los colores en el spinner
    private fun cargarColores() {
        colors = arrayOf("Red", "Blue", "White", "Yellow", "Black", "Green", "Purple", "Orange", "Grey")
        addColors(colors)
    }

    private fun addColors(colors: Array<String?>) {
        val spinnerArrayAdapter =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, colors)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainSpinnerColores.adapter = spinnerArrayAdapter
    }

    // Abrimos la actividad de mostrar datos
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
        this.nombre = mainEditNombre.text.toString()
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

    // Opciones a pulsar un estado del menú
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

    // Opción de menu
    private fun menuOtraOpcion() {
        // Escribimos en el log
        // https://developer.android.com/studio/debug/am-logcat?hl=es
        Log.i("ETIQUETA", "Mensaje de log")
        Toast.makeText(this, "Has pulsado Otra Opción", Toast.LENGTH_SHORT).show()
    }

    // Muestra la actividad Acerca De
    private fun menuAcercaDe() {
        val intent = Intent(this, AcercaDeActivity::class.java)
        startActivity(intent)
    }

    /**
     * CICLO DE VIDA
     */
    // https://developer.android.com/guide/components/activities/activity-lifecycle?hl=es
    override fun onStart() {
        super.onStart()
        Log.i("CICLO", "Start")
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i("CICLO", "Resume")
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
        Log.i("CICLO", "Pause")
        super.onPause()
    }

    override fun onStop() {
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
        Log.i("CICLO", "Stop")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("CICLO", "Restart")
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
        Log.i("CICLO", "Destroy")
        super.onDestroy()
    }

    // Para salvar el estado por ejemplo es usando un Bundle en el ciclo de vida
    override fun onSaveInstanceState(outState: Bundle) {
        // Salvamos en un bundle estas variables o estados de la interfaz
        outState.run {
            // Actualizamos los datos o los recogemos de la interfaz
            currentScore *= 2
            currentLevel++
            putInt("PUNTUACION", currentScore)
            putInt("NIVEL", currentLevel)
            putString("NOMBRE", nombre)
            putStringArray("COLORES", colors);
            Log.i("CICLO", "Salvando el estado con Puntuación: $currentScore - nivel: $currentLevel - $nombre")
        }
        // Siempre se llama a la superclase para salvar as cosas
        super.onSaveInstanceState(outState)
    }

    // Para recuperar el estado al volver al un estado de ciclo de vida de la Interfaz
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Recuperamos en un bundle estas variables o estados de la interfaz
        super.onRestoreInstanceState(savedInstanceState)
        // Recuperamos del Bundle
        savedInstanceState.run {
            currentScore = getInt("PUNTUACION")
            currentLevel = getInt("NIVEL")
            nombre = getString("NOMBRE").toString()
            colors = getStringArray("COLORES") as Array<String?>
            addColors(colors);
            Log.i("CICLO", "Recuperando el estado con Puntuación: $currentScore - nivel: $currentLevel - $nombre")
        }
    }
}