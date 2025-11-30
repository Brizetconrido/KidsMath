package com.example.kidsmath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MultiGameActivity extends AppCompatActivity {

    TextView txtNum1Multi, txtNum2Multi, txtResultMulti;
    EditText edtAnswerMulti;
    Button btnCheckMulti, btnNextMulti;

    int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        // Referencias XML correctas
        txtNum1Multi = findViewById(R.id.txtNum1Multi);
        txtNum2Multi = findViewById(R.id.txtNum2Multi);
        txtResultMulti = findViewById(R.id.txtResultMulti);
        edtAnswerMulti = findViewById(R.id.edtAnswerMulti);
        btnCheckMulti = findViewById(R.id.btnCheckMulti);
        btnNextMulti = findViewById(R.id.btnNextMulti);

        generateNewOperation();

        // Botón comprobar
        btnCheckMulti.setOnClickListener(v -> {
            String answer = edtAnswerMulti.getText().toString();

            if (answer.isEmpty()) {
                Toast.makeText(this, "Ingrese una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(answer) == a * b) {
                txtResultMulti.setText("¡Correcto!");
                txtResultMulti.setTextColor(0xFF2E7D32);
            } else {
                txtResultMulti.setText("Incorrecto");
                txtResultMulti.setTextColor(0xFFC62828);
            }
        });

        // Botón siguiente
        btnNextMulti.setOnClickListener(v -> {
            generateNewOperation();
            txtResultMulti.setText("");
        });
    }

    private void generateNewOperation() {
        Random random = new Random();
        a = random.nextInt(10);
        b = random.nextInt(10);

        txtNum1Multi.setText(String.valueOf(a));
        txtNum2Multi.setText(String.valueOf(b));
        edtAnswerMulti.setText("");
    }
}
