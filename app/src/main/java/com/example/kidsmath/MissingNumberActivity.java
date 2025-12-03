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

public class MissingNumberActivity extends AppCompatActivity {

    TextView txtNumA, txtMissingSlot, txtNumC, txtNumD, txtResultMissing;
    EditText edtAnswerMissing;
    Button btnCheckMissing, btnNextMissing, btnVolver;

    int missingNumber;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_number);

        db = new DatabaseManager(this);

        // IDs EXACTOS DEL XML
        txtNumA = findViewById(R.id.txtNumA);
        txtMissingSlot = findViewById(R.id.txtMissingSlot);
        txtNumC = findViewById(R.id.txtNumC);
        txtNumD = findViewById(R.id.txtNumD);
        txtResultMissing = findViewById(R.id.txtResultMissing);

        edtAnswerMissing = findViewById(R.id.edtAnswerMissing);

        btnCheckMissing = findViewById(R.id.btnCheckMissing);
        btnNextMissing = findViewById(R.id.btnNextMissing);  // ← NUEVO
        btnVolver = findViewById(R.id.btnVolver);

        generateSequence();

        // ➤ BOTÓN COMPROBAR
        btnCheckMissing.setOnClickListener(v -> {

            String answerTxt = edtAnswerMissing.getText().toString().trim();

            if (answerTxt.isEmpty()) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show();
                return;
            }

            int answer = Integer.parseInt(answerTxt);

            if (answer == missingNumber) {

                txtResultMissing.setText("¡Correcto +1 punto!");
                txtResultMissing.setTextColor(0xFF2E7D32);

                db.addScore("numero_faltante", 1);

                new Handler().postDelayed(() -> {
                    generateSequence();
                    txtResultMissing.setText("");
                    edtAnswerMissing.setText("");
                }, 900);

            } else {
                txtResultMissing.setText("Incorrecto");
                txtResultMissing.setTextColor(0xFFC62828);
            }
        });

        //  BOTÓN SIGUIENTE (SIN SUMAR PUNTOS)
        btnNextMissing.setOnClickListener(v -> {
            generateSequence();
            txtResultMissing.setText("");
            edtAnswerMissing.setText("");
            Toast.makeText(this, "Nuevo ejercicio ➤", Toast.LENGTH_SHORT).show();
        });

        //  BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(MissingNumberActivity.this, MainMenuActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void generateSequence() {
        Random random = new Random();

        int start = random.nextInt(10);
        int step = random.nextInt(3) + 1;

        int numA = start;
        missingNumber = start + step;
        int numC = start + (step * 2);
        int numD = start + (step * 3);

        txtNumA.setText(String.valueOf(numA));
        txtMissingSlot.setText("?");
        txtNumC.setText(String.valueOf(numC));
        txtNumD.setText(String.valueOf(numD));
    }
}

