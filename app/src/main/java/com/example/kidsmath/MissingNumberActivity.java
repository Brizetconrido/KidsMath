package com.example.kidsmath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MissingNumberActivity extends AppCompatActivity {

    TextView txtNumA, txtMissingSlot, txtNumC, txtNumD, txtResultMissing;
    EditText edtAnswerMissing;
    Button btnCheckMissing, btnNextMissing;

    int missingNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_number);


        txtNumA = findViewById(R.id.txtNumA);
        txtMissingSlot = findViewById(R.id.txtMissingSlot);
        txtNumC = findViewById(R.id.txtNumC);
        txtNumD = findViewById(R.id.txtNumD);

        edtAnswerMissing = findViewById(R.id.edtAnswerMissing);
        txtResultMissing = findViewById(R.id.txtResultMissing);

        btnCheckMissing = findViewById(R.id.btnCheckMissing);
        btnNextMissing = findViewById(R.id.btnNextMissing);

        generateSequence();

        btnCheckMissing.setOnClickListener(v -> {

            String answerTxt = edtAnswerMissing.getText().toString().trim();

            if (answerTxt.isEmpty()) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show();
                return;
            }

            int answer = Integer.parseInt(answerTxt);

            if (answer == missingNumber) {
                txtResultMissing.setText("¡Correcto!");
                txtResultMissing.setTextColor(0xFF2E7D32);
            } else {
                txtResultMissing.setText("Incorrecto");
                txtResultMissing.setTextColor(0xFFC62828);
            }
        });

        btnNextMissing.setOnClickListener(v -> {
            generateSequence();
            txtResultMissing.setText("");
            edtAnswerMissing.setText("");
        });
    }

    private void generateSequence() {
        Random random = new Random();

        int start = random.nextInt(10); // Número inicial
        int step = random.nextInt(3) + 1; // Paso (1–3)

        // A - ? - C - D (ejemplo: 2 - ? - 6 - 8)
        int numA = start;
        missingNumber = start + step;
        int numC = start + step * 2;
        int numD = start + step * 3;

        txtNumA.setText(String.valueOf(numA));
        txtMissingSlot.setText("?");
        txtNumC.setText(String.valueOf(numC));
        txtNumD.setText(String.valueOf(numD));
    }
}

