package com.example.kidsmath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class DivGameActivity extends AppCompatActivity {

    TextView txtNum1Div, txtNum2Div, txtResultDiv;
    EditText edtAnswerDiv;
    Button btnCheckDiv, btnNextDiv, btnVolver;

    int a, b; // a / b = resultado exacto

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div_game);

        db = new DatabaseManager(this);

        txtNum1Div = findViewById(R.id.txtNum1Div);
        txtNum2Div = findViewById(R.id.txtNum2Div);
        txtResultDiv = findViewById(R.id.txtResultDiv);
        edtAnswerDiv = findViewById(R.id.edtAnswerDiv);
        btnCheckDiv = findViewById(R.id.btnCheckDiv);
        btnNextDiv = findViewById(R.id.btnNextDiv);  // ← NUEVO
        btnVolver = findViewById(R.id.btnVolver);

        generarDivision();

        // BOTÓN COMPROBAR
        btnCheckDiv.setOnClickListener(v -> {

            String respuestaTxt = edtAnswerDiv.getText().toString().trim();

            if (respuestaTxt.isEmpty()) {
                Toast.makeText(this, "Ingresa una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            int respuesta = Integer.parseInt(respuestaTxt);

            if (respuesta == a / b) {

                txtResultDiv.setText("¡Correcto +1 punto!");
                txtResultDiv.setTextColor(0xFF2E7D32);

                db.addScore("divisiones", 1);

                new Handler().postDelayed(() -> {
                    generarDivision();
                    txtResultDiv.setText("");
                    edtAnswerDiv.setText("");
                }, 900);

            } else {
                txtResultDiv.setText("Incorrecto");
                txtResultDiv.setTextColor(0xFFC62828);
            }
        });

        //  BOTÓN SIGUIENTE (sin sumar puntos)
        btnNextDiv.setOnClickListener(v -> {
            generarDivision();
            txtResultDiv.setText("");
            edtAnswerDiv.setText("");
            Toast.makeText(this, "Nuevo ejercicio ➤", Toast.LENGTH_SHORT).show();
        });

        //  BOTÓN VOLVER AL MENÚ DE JUEGOS
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(DivGameActivity.this, GameSelectionActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void generarDivision() {
        Random r = new Random();

        b = r.nextInt(9) + 1;
        int resultado = r.nextInt(10);

        a = b * resultado;

        txtNum1Div.setText(String.valueOf(a));
        txtNum2Div.setText(String.valueOf(b));
    }
}
