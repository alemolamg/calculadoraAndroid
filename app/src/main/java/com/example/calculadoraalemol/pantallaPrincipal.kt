package com.example.calculadoraalemol

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class pantallaPrincipal : AppCompatActivity() {
    private var btnLimpiar: Button? = null
    private var btnIgual: Button? = null
    private var btnSuma: Button? = null
    private var btnResta: Button? = null
    private var btnMulti: Button? = null
    private var btnDiv: Button? = null
    private var btnDecimal: Button? = null
    private var hayDecimales = false
    private var btnCambioSigno: Button? = null
    private val botonesNumeros: MutableList<Button> = mutableListOf()
    private var numeroStr: String? = null
    private var operacion = 0
    var sigNumero = 0.0
    var resultado = 0.0
    private var ventana: Window? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        numeroStr = ""
        sigNumero = 0.0
        resultado = 0.0
        operacion = VACIA
        creaBotones()

        // Cambio color ventana
        ventana = window
        ventana!!.statusBarColor = Color.parseColor("#00584C") //Barra arriba oscura
        //this.ventana.setStatusBarColor(Color.parseColor("#008D7D"));    //Barra arriba clara
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00584C"))) //Barra arriba oscura
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008D7D")));    //Barra arriba clara
    }

    /**
     * Método para crear botones
     */
    private fun creaBotones() {
        // Todos los botones números se pulsan de la misma manera, así que los meto en una lista.
        botonesNumeros.add(findViewById(R.id.boton0))
        botonesNumeros.add(findViewById(R.id.boton1))
        botonesNumeros.add(findViewById(R.id.boton2))
        botonesNumeros.add(findViewById(R.id.boton3))
        botonesNumeros.add(findViewById(R.id.boton4))
        botonesNumeros.add(findViewById(R.id.boton5))
        botonesNumeros.add(findViewById(R.id.boton6))
        botonesNumeros.add(findViewById(R.id.boton7))
        botonesNumeros.add(findViewById(R.id.boton8))
        botonesNumeros.add(findViewById(R.id.boton9))
        for (boton in botonesNumeros) pulsarNum(boton)

        // Genero el resto de botones
        btnIgual = findViewById(R.id.botonIgual)
        btnLimpiar = findViewById(R.id.botonLimpiar)
        btnSuma = findViewById(R.id.botonSuma)
        btnResta = findViewById(R.id.botonResta)
        btnMulti = findViewById(R.id.botonMultiplicar)
        btnDiv = findViewById(R.id.botonDividir)
        btnDecimal = findViewById(R.id.botonDecimal)
        btnCambioSigno = findViewById(R.id.botonCambioSigno)
        pulsarOperaciones()
    }

    /**
     * Método para generar todas las pulsaciones.
     */
    private fun pulsarOperaciones() {
        pulsarSuma()
        pulsarResta()
        pulsarDiv()
        pulsarMult()
        pulsarIgual()
        pulsarLimpiar()
        pulsarDecimal()
        pulsarCambioSigno()
    }

    private fun pulsarCambioSigno() {
        btnCambioSigno!!.setOnClickListener { operacionCamSigno() }
    }

    /**
     * añade los decimales
     * Versión Beta
     */
    private fun pulsarDecimal() {  //Beta
        btnDecimal!!.setOnClickListener { operacionDecimal() }
    }

    /**
     * Pulsación de botón Multiplicación
     */
    private fun pulsarMult() {
        btnMulti!!.setOnClickListener { operacionMult() }
    }

    /**
     * Pulsación de botón Dividir
     */
    private fun pulsarDiv() {
        btnDiv!!.setOnClickListener { operacionDiv() }
    }

    /**
     * Pulsación de botón Resta
     */
    private fun pulsarResta() {
        btnResta!!.setOnClickListener { // ((TextView)findViewById(R.id.pantalla)).setText("- - -");
            operacionResta()
        }
    }

    /**
     * Pulsación de botón limpiar
     */
    private fun pulsarLimpiar() {
        btnLimpiar!!.setOnClickListener { operacionLimpiar() }
    }

    /**
     * Pulsación de botón suma
     */
    private fun pulsarSuma() {
        btnSuma!!.setOnClickListener { operacionSuma() }
    }

    /**
     * Pulsación de botón igual
     */
    fun pulsarIgual() {
        btnIgual!!.setOnClickListener { operacionIgual() }
    }

    /**
     * Método para llamar a la pulsación de todos los botones
     * @param boton (Button) botón pulsado.
     */
    private fun pulsarNum(boton: Button) {
        boton.setOnClickListener {
            numeroStr = if ((findViewById<View>(R.id.pantalla) as TextView).text === "0" ||
                    (findViewById<View>(R.id.pantalla) as TextView).text === "0.0") "" + boton.text else numeroStr + boton.text //Añado el número pulsado
            cargarEnPantalla(numeroStr!!)
        }
    }

    /**
     * operación de Igual
     * llama al resto de operaciones si hace falta.
     */
    fun operacionIgual() {
        when (operacion) {
            RESTA -> operacionResta()
            SUMA -> operacionSuma()
            DIVISION -> operacionDiv()
            MULTIPLICACION -> operacionMult()
            VACIA -> {
                if (numeroStr !== "") {
                    resultado = numeroStr!!.toDouble()
                    numeroStr = ""
                    desactivarHayDecimales()
                }
                operacion = VACIA
                cargarEnPantalla(resultado)
            }
        }
        //operacion = VACIA;
    }

    /**
     * Cambia el signo del operador
     */
    private fun operacionCamSigno() {
        if (numeroStr === "") {
            resultado = resultado * -1
            cargarEnPantalla(resultado)
        } else {
            val num = numeroStr!!.toDouble() * -1
            numeroStr = "" + num
            cargarEnPantalla(numeroStr!!)
        }
    }

    /**
     * operación de multiplicar
     */
    private fun operacionMult() {
        if (operacion != VACIA && operacion != MULTIPLICACION) operacionIgual()
        sigNumero = if (numeroStr !== "") numeroStr!!.toDouble() else 1.0
        if (operacion == VACIA) resultado = sigNumero else resultado *= sigNumero
        operacion = MULTIPLICACION
        numeroStr = ""
        desactivarHayDecimales()
        cargarEnPantalla(resultado)
    }

    /**
     * operación para dividir
     */
    private fun operacionDiv() {
        if (operacion != VACIA && operacion != DIVISION) operacionIgual()
        sigNumero = if (numeroStr !== "") numeroStr!!.toDouble() else 1.0
        if (operacion == VACIA) resultado = sigNumero // Hasta aquí si
        else {
            if (sigNumero != 0.0) resultado = resultado / sigNumero else resultado = 0.0 //Añadir un mensaje en pantalla y luego a 0
        }
        operacion = DIVISION
        numeroStr = ""
        desactivarHayDecimales()
        cargarEnPantalla(resultado)
    }

    /**
     * Limpiamos toda la calculadora
     */
    private fun operacionLimpiar() {
        sigNumero = 0.0
        resultado = 0.0
        operacion = VACIA
        numeroStr = ""
        hayDecimales = false
        cargarEnPantalla(resultado)
    }

    /**
     * Operación de suma
     */
    private fun operacionSuma() {
        if (operacion != VACIA && operacion != SUMA) operacionIgual()
        sigNumero = if (numeroStr !== "") numeroStr!!.toDouble() else 0.0
        if (operacion == VACIA) {   //Deja el primer número en positivo
            resultado = sigNumero
        } else {
            resultado = resultado + sigNumero
        }
        operacion = SUMA
        numeroStr = ""
        desactivarHayDecimales()
        cargarEnPantalla(resultado)
    }

    /**
     * Operación de resta
     */
    private fun operacionResta() {
        if (operacion != VACIA && operacion != RESTA) operacionIgual()
        sigNumero = if (numeroStr !== "") numeroStr!!.toDouble() else 0.0
        resultado = if (operacion == VACIA) {   //Deja el primer número en positivo
            sigNumero
        } else {
            resultado - sigNumero
        }

        //this.resultado = resultado - sigNumero;
        operacion = RESTA
        numeroStr = ""
        desactivarHayDecimales()
        cargarEnPantalla(resultado)
    }

    private fun operacionDecimal() {
        if (hayDecimales == false) {
            numeroStr = numeroStr + btnDecimal!!.text //Añado el número pulsado
            cargarEnPantalla(numeroStr!!)
            hayDecimales = true
        }
    }

    private fun cargarEnPantalla(texto: String) {
        (findViewById<View>(R.id.pantalla) as TextView).text = texto // Debería cambiar la pantalla
    }

    private fun cargarEnPantalla(num: Double) {
        val entero = num.toLong()
        if (num - entero != 0.0) (findViewById<View>(R.id.pantalla) as TextView).text = "" + num else (findViewById<View>(R.id.pantalla) as TextView).text = "" + entero
    }

    private fun desactivarHayDecimales() {
        hayDecimales = false
    }

    companion object {
        // Variables de operaciones elegidas.
        const val VACIA = 0
        const val SUMA = 2
        const val RESTA = 1
        const val MULTIPLICACION = 4
        const val DIVISION = 3
    }
}