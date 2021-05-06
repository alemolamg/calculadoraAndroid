
package com.example.calculadoraalemol;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class pantallaPrincipal extends AppCompatActivity {

    // Variables de operaciones elegidas.
    public static final int VACIA = 0;
    public static final int SUMA = 2;
    public static final int RESTA = 1;
    public static final int MULTIPLICACION = 4;
    public static final int DIVISION = 3;

    private Button btnLimpiar, btnIgual;
    private Button btnSuma, btnResta, btnMulti, btnDiv;

    private Button btnDecimal;
    private boolean hayDecimales = false;

    private Button btnCambioSigno;

    private List<Button> botonesNumeros = new ArrayList<Button>();
    private String numeroStr;
    private int operacion;
    double sigNumero, resultado;

    private Window ventana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        numeroStr ="";
        sigNumero = 0;
        resultado = 0;
        operacion = VACIA;

        creaBotones();

        // Cambio color ventana
        this.ventana = getWindow();
        this.ventana.setStatusBarColor(Color.parseColor("#00695C"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00695C")));
    }

    /**
     * Método para crear botones
     */
    private void creaBotones(){
        // Todos los botones números se pulsan de la misma manera, así que los meto en una lista.
        botonesNumeros.add(findViewById(R.id.boton0));
        botonesNumeros.add(findViewById(R.id.boton1));
        botonesNumeros.add(findViewById(R.id.boton2));
        botonesNumeros.add(findViewById(R.id.boton3));
        botonesNumeros.add(findViewById(R.id.boton4));
        botonesNumeros.add(findViewById(R.id.boton5));
        botonesNumeros.add(findViewById(R.id.boton6));
        botonesNumeros.add(findViewById(R.id.boton7));
        botonesNumeros.add(findViewById(R.id.boton8));
        botonesNumeros.add(findViewById(R.id.boton9));

        for (Button boton : botonesNumeros)
            pulsarNum(boton);

        // Genero el resto de botones
        btnIgual = findViewById(R.id.botonIgual);
        btnLimpiar = findViewById(R.id.botonLimpiar);
        btnSuma = findViewById(R.id.botonSuma);
        btnResta = findViewById(R.id.botonResta);
        btnMulti = findViewById(R.id.botonMultiplicar);
        btnDiv = findViewById(R.id.botonDividir);
        btnDecimal = findViewById(R.id.botonDecimal);
        btnCambioSigno = findViewById(R.id.botonCambioSigno);

        pulsarOperaciones();
    }


    /**
     * Método para generar todas las pulsaciones.
     */
    private void pulsarOperaciones() {
        pulsarSuma();
        pulsarResta();
        pulsarDiv();
        pulsarMult();
        pulsarIgual();
        pulsarLimpiar();
        pulsarDecimal();
        pulsarCambioSigno();
    }

    private void pulsarCambioSigno() {
        btnCambioSigno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionCamSigno();
            }
        });
    }

    /**
     * añade los decimales
     * Versión Beta
     */
    private void pulsarDecimal() {  //Beta
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionDecimal();
            }
        });
    }

    /**
     * Pulsación de botón Multiplicación
     */
    private void pulsarMult() {
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionMult();
            }
        });
    }

    /**
     * Pulsación de botón Dividir
     */
    private void pulsarDiv() {
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionDiv();
            }
        });
    }

    /**
     * Pulsación de botón Resta
     */
    private void pulsarResta() {
        btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ((TextView)findViewById(R.id.pantalla)).setText("- - -");
                operacionResta();
            }
        });

    }

    /**
     * Pulsación de botón limpiar
     */
    private void pulsarLimpiar(){
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionLimpiar();
            }
        });
    }

    /**
     * Pulsación de botón suma
     */
    private void pulsarSuma(){
        btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionSuma();
            }
        });
    }

    /**
     * Pulsación de botón igual
     */
    public void pulsarIgual(){
        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionIgual();
            }
        });
    }

    /**
     * Método para llamar a la pulsación de todos los botones
     * @param boton (Button) botón pulsado.
     */
    private void pulsarNum(Button boton){
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroStr = numeroStr + boton.getText();    //Añado el número pulsado
                cargarEnPantalla(numeroStr);
            }
        });
    }

    /**
     * operación de Igual
     * llama al resto de operaciones si hace falta.
     */
    public void operacionIgual(){
        switch (operacion) {
            case RESTA: operacionResta(); break;
            case SUMA: operacionSuma(); break;
            case DIVISION: operacionDiv(); break;
            case MULTIPLICACION: operacionMult(); break;
            case VACIA:
            default:
                if(numeroStr != "") {
                    resultado = Double.parseDouble(numeroStr);
                    numeroStr = "";
                    desactivarHayDecimales();
                }
                operacion = VACIA;
                cargarEnPantalla(resultado); break;
        }

    }

    /**
     * Cambia el signo del operador
     */
    private void operacionCamSigno(){
        if(numeroStr ==""){
            resultado = resultado *(-1);
            cargarEnPantalla(resultado);
        } else {
            double num = Double.parseDouble(numeroStr) * (-1);
            numeroStr = "" + num;
            cargarEnPantalla(numeroStr);
        }
    }


    /**
     * operación de multiplicar
     */
    private void operacionMult() {
        if(operacion != VACIA && operacion != MULTIPLICACION)
            operacionIgual();

        if(numeroStr != "")
            sigNumero = Double.parseDouble(numeroStr);
        else
            sigNumero = 1;

        if (operacion == VACIA)
            this.resultado = sigNumero;
        else
            this.resultado = resultado * sigNumero;
        this.operacion = MULTIPLICACION;
        numeroStr = "";
        desactivarHayDecimales();
        cargarEnPantalla(resultado);
    }

    /**
     * operación para dividir
     */
    private void operacionDiv() {
        if(operacion != VACIA && operacion != DIVISION)
            operacionIgual();

        if(numeroStr != "")
            sigNumero = Double.parseDouble(numeroStr);
        else
            sigNumero = 1;

        if (operacion == VACIA)
           this.resultado = sigNumero;   // Hasta aquí si
        else {
            if (sigNumero !=0)
               this.resultado = resultado / sigNumero;
            else
                this.resultado = 0; //Añadir un mensaje en pantalla y luego a 0
        }
        this.operacion = DIVISION;
        numeroStr = "";
        desactivarHayDecimales();
        cargarEnPantalla(resultado);
    }

    /**
     * Limpiamos toda la calculadora
     */
    private void operacionLimpiar(){
        this.sigNumero = 0;
        this.resultado = 0;
        this.operacion = VACIA;
        this.numeroStr = "";
        this.hayDecimales = false;
        cargarEnPantalla(resultado);
    }

    /**
     * Operación de suma
     */
    private void operacionSuma(){
        if(operacion != VACIA && operacion != SUMA)
            operacionIgual();

        if(numeroStr != "")
            sigNumero = Double.parseDouble(numeroStr);
        else
            sigNumero = 0;

        if (operacion == VACIA) {   //Deja el primer número en positivo
            this.resultado = sigNumero;
        } else {
            this.resultado = resultado + sigNumero;
        }

        this.operacion = SUMA;
        numeroStr = "";
        desactivarHayDecimales();
        cargarEnPantalla(resultado);
    }


    /**
     * Operación de resta
     */
    private void operacionResta() {
        if(operacion != VACIA && operacion != RESTA)
            operacionIgual();

        if(numeroStr != "")
            sigNumero = Double.parseDouble(numeroStr);
        else
            sigNumero = 0;

        if (operacion == VACIA) {   //Deja el primer número en positivo
            this.resultado = sigNumero;
        } else {
            this.resultado = resultado - sigNumero;
        }

        //this.resultado = resultado - sigNumero;
        this.operacion = RESTA;
        numeroStr = "";
        desactivarHayDecimales();
        cargarEnPantalla(resultado);
    }

    private void operacionDecimal(){

        if (hayDecimales == false) {
            numeroStr = numeroStr + btnDecimal.getText();    //Añado el número pulsado
            cargarEnPantalla(numeroStr);
            hayDecimales = true;
        }
    }


    private void cargarEnPantalla(String texto){
        ((TextView) findViewById(R.id.pantalla)).setText(texto);    // Debería cambiar la pantalla
    }

    private void cargarEnPantalla(double num){
        ((TextView) findViewById(R.id.pantalla)).setText(""+num);
    }

    private void desactivarHayDecimales() {
        this.hayDecimales = false;
    }
}