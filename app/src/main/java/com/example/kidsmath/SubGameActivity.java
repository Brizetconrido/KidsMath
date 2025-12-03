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

public class SubGameActivity extends AppCompatActivity {

    TextView txtNum1Sub, txtNum2Sub, txtResultSub;
    EditText edtAnswerSub;
    Button btnCheckSub, btnSiguiente, btnVolver;

    int a, b;

    DatabaseManager db;
    Handler handler = new Handler(); // para delay de 1 segundo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_game);


        txtNum1Sub = findViewById(R.id.txtNum1);
        txtNum2Sub = findViewById(R.id.txtNum2);
        txtResultSub = findViewById(R.id.txtResultSub);
        edtAnswerSub = findViewById(R.id.edtAnswerSub);
        btnCheckSub = findViewById(R.id.btnCheckSub);
        btnSiguiente = findViewById(R.id.btnNextSub); // ← NUEVO
        btnVolver = findViewById(R.id.btnVolver);

        db = new DatabaseManager(this);

        generateSub();

        // BOTÓN COMPROBAR RESPUESTA
        btnCheckSub.setOnClickListener(v -> {

            String answerTxt = edtAnswerSub.getText().toString().trim();

            if (answerTxt.isEmpty()) {
                Toast.makeText(this, "Ingresa una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            int answer = Integer.parseInt(answerTxt);
            int correct = a - b;

            if (answer == correct) {

                txtResultSub.setText("✔ Correcto! +1 punto");
                txtResultSub.setTextColor(0xFF2ECC71);

                db.addScore("restas", 1);

                handler.postDelayed(() -> {
                    txtResultSub.setText("");
                    edtAnswerSub.setText("");
                    generateSub();
                }, 900);

            } else {

                txtResultSub.setText("✘ Incorrecto");
                txtResultSub.setTextColor(0xFFE74C3C);
            }
        });

        //  BOTÓN SIGUIENTE (NO suma puntos)
        btnSiguiente.setOnClickListener(v -> {
            generateSub();
            edtAnswerSub.setText("");
            txtResultSub.setText("");
            Toast.makeText(this, "Nuevo ejercicio ➤", Toast.LENGTH_SHORT).show();
        });

        // VOLVER AL MENÚ
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(SubGameActivity.this, MainMenuActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void generateSub() {
        Random r = new Random();
        a = r.nextInt(10) + 5;
        b = r.nextInt(10);

        txtNum1Sub.setText(String.valueOf(a));
        txtNum2Sub.setText(String.valueOf(b));
    }
}
