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

public class SumGameActivity extends AppCompatActivity {

    TextView txtOperacion;
    EditText edtRespuesta;
    Button btnComprobar, btnSiguiente, btnVolver;

    int a, b;

    DatabaseManager db;
    Handler handler = new Handler(); // Para delay de 1 segundo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_game);

        txtOperacion = findViewById(R.id.txtOperacion);
        edtRespuesta = findViewById(R.id.edtRespuesta);
        btnComprobar = findViewById(R.id.btnComprobar);

        // ← NUEVO: botón siguiente
        btnSiguiente = findViewById(R.id.btnSiguiente);

        // Botón volver
        btnVolver = findViewById(R.id.btnVolver);

        db = new DatabaseManager(this);

        generateSum();

        // ➤ Botón comprobar
        btnComprobar.setOnClickListener(v -> {

            String answer = edtRespuesta.getText().toString().trim();

            if (answer.isEmpty()) {
                Toast.makeText(this, "Ingrese una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(answer) == a + b) {

                Toast.makeText(this, "✔ Correcto! +1 punto", Toast.LENGTH_SHORT).show();

                db.addScore("sumas", 1);

                handler.postDelayed(() -> {
                    generateSum();
                    edtRespuesta.setText("");
                }, 1000);

            } else {
                Toast.makeText(this, "✘ Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

        // ➤ NUEVO: Botón Siguiente (NO suma puntos)
        btnSiguiente.setOnClickListener(v -> {
            generateSum();
            edtRespuesta.setText("");
            Toast.makeText(this, "Nuevo ejercicio ➤", Toast.LENGTH_SHORT).show();
        });

        // ➤ Botón Volver
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(SumGameActivity.this, MainMenuActivity.class);
            startActivity(i);
            finish();
        });
    }

    // Botón físico atrás
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SumGameActivity.this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }

    private void generateSum() {
        Random r = new Random();
        a = r.nextInt(10);
        b = r.nextInt(10);

        txtOperacion.setText(a + " + " + b + " = ?");
    }
}

