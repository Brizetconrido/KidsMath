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

public class MultiGameActivity extends AppCompatActivity {

    TextView txtNum1Multi, txtNum2Multi, txtResultMulti;
    EditText edtAnswerMulti;
    Button btnCheckMulti, btnNextMulti, btnVolver;

    int a, b;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        db = new DatabaseManager(this);


        txtNum1Multi = findViewById(R.id.txtNum1Multi);
        txtNum2Multi = findViewById(R.id.txtNum2Multi);
        txtResultMulti = findViewById(R.id.txtResultMulti);
        edtAnswerMulti = findViewById(R.id.edtAnswerMulti);

        btnCheckMulti = findViewById(R.id.btnCheckMulti);
        btnNextMulti = findViewById(R.id.btnNextMulti);  // ← FALTABA
        btnVolver = findViewById(R.id.btnVolver);

        generarMultiplicacion();

        //  BOTÓN COMPROBAR
        btnCheckMulti.setOnClickListener(v -> {

            String respTxt = edtAnswerMulti.getText().toString().trim();

            if (respTxt.isEmpty()) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show();
                return;
            }

            int respuesta = Integer.parseInt(respTxt);

            if (respuesta == a * b) {

                txtResultMulti.setText("¡Correcto! +1 punto");
                txtResultMulti.setTextColor(0xFF2E7D32);

                db.addScore("multiplicaciones", 1);

                new Handler().postDelayed(() -> {
                    generarMultiplicacion();
                    txtResultMulti.setText("");
                    edtAnswerMulti.setText("");
                }, 900);

            } else {
                txtResultMulti.setText("Incorrecto");
                txtResultMulti.setTextColor(0xFFC62828);
            }
        });

        //  BOTÓN SIGUIENTE (SIN SUMAR PUNTOS)
        btnNextMulti.setOnClickListener(v -> {
            generarMultiplicacion();
            txtResultMulti.setText("");
            edtAnswerMulti.setText("");
            Toast.makeText(this, "Nuevo ejercicio ➤", Toast.LENGTH_SHORT).show();
        });

        // BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(MultiGameActivity.this, MainMenuActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void generarMultiplicacion() {
        Random r = new Random();
        a = r.nextInt(10) + 1;
        b = r.nextInt(10) + 1;

        txtNum1Multi.setText(String.valueOf(a));
        txtNum2Multi.setText(String.valueOf(b));
    }
}
