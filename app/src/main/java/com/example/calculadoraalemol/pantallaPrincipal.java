
package com.example.calculadoraalemol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class pantallaPrincipal extends AppCompatActivity {


    private Button btnLimpiar, btnIgual;
    private Button btnSuma, btnResta, btnMulti, btnDiv;
    private List<Button> botonesNumeros = new ArrayList<Button>();
    private EditText etProceso, etconcatenar;
    private String operador1, operador2;
    double numero1, numero2, resultado;

    // private TextView textView = R.id.pantalla;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        operador2 ="";
        operador1 ="";

        creaBotones();
    }

    private void creaBotones(){
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

        btnIgual = findViewById(R.id.botonIgual);
        btnLimpiar = findViewById(R.id.botonLimpiar);
        btnSuma = findViewById(R.id.botonSuma);

    }


    private void pulsarNum(Button boton){
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador2 = operador2 + boton.getText();    //Añado el número pulsado
                cargarEnPantalla(operador2);
            }
        });
    }


    private void cargarEnPantalla(String texto){
        ((TextView) findViewById(R.id.pantalla)).setText(texto);    // Debería cambiar la pantalla
    }
}