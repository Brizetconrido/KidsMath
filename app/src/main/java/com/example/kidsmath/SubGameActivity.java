package com.example.kidsmath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SubGameActivity extends AppCompatActivity {

    TextView txtNum1, txtNum2, txtResultSub;
    EditText edtAnswerSub;
    Button btnCheckSub, btnNextSub;

    int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_game);

        // 🔹 Vincular con IDs reales del XML
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        edtAnswerSub = findViewById(R.id.edtAnswerSub);
        btnCheckSub = findViewById(R.id.btnCheckSub);
        txtResultSub = findViewById(R.id.txtResultSub);
        btnNextSub = findViewById(R.id.btnNextSub);

        generateSub();

        // Botón Comprobar
        btnCheckSub.setOnClickListener(v -> {
            String answer = edtAnswerSub.getText().toString();

            if (answer.isEmpty()) {
                Toast.makeText(this, "Ingrese una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = a - b;

            if (Integer.parseInt(answer) == result) {
                txtResultSub.setText("✔ Correcto!");
                txtResultSub.setTextColor(0xFF2ECC71); // verde
            } else {
                txtResultSub.setText("✘ Incorrecto. Respuesta: " + result);
                txtResultSub.setTextColor(0xFFE74C3C); // rojo
            }
        });

        // Botón Siguiente
        btnNextSub.setOnClickListener(v -> {
            generateSub();
            txtResultSub.setText("");
        });
    }

    private void generateSub() {
        Random r = new Random();

        // Para evitar resultados negativos
        a = r.nextInt(10) + 5;  // 5 a 14
        b = r.nextInt(5);       // 0 a 4

        txtNum1.setText(String.valueOf(a));
        txtNum2.setText(String.valueOf(b));
        edtAnswerSub.setText("");
    }
}
