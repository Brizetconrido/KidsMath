package com.example.kidsmath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class DivGameActivity extends AppCompatActivity {

    TextView txtNum1Div, txtNum2Div, txtResultDiv;
    EditText edtAnswerDiv;
    Button btnCheckDiv, btnNextDiv;

    int a, b; // a / b = resultado exacto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div_game);

        txtNum1Div = findViewById(R.id.txtNum1Div);
        txtNum2Div = findViewById(R.id.txtNum2Div);
        txtResultDiv = findViewById(R.id.txtResultDiv);

        edtAnswerDiv = findViewById(R.id.edtAnswerDiv);
        btnCheckDiv = findViewById(R.id.btnCheckDiv);
        btnNextDiv = findViewById(R.id.btnNextDiv);

        generateDivision();

        btnCheckDiv.setOnClickListener(v -> {
            String ans = edtAnswerDiv.getText().toString().trim();

            if (ans.isEmpty()) {
                Toast.makeText(this, "Ingrese una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            int answer = Integer.parseInt(ans);

            if (answer == a / b) {
                txtResultDiv.setText("¡Correcto!");
                txtResultDiv.setTextColor(0xFF2E7D32); // Verde
            } else {
                txtResultDiv.setText("Incorrecto");
                txtResultDiv.setTextColor(0xFFC62828); // Rojo
            }
        });

        btnNextDiv.setOnClickListener(v -> {
            generateDivision();
            edtAnswerDiv.setText("");
            txtResultDiv.setText("");
        });
    }

    private void generateDivision() {
        Random r = new Random();

        b = r.nextInt(9) + 1;  // divisor (1–9)
        int resultado = r.nextInt(10); // resultado (0–9)

        a = b * resultado;  // para asegurar que a / b sea exacto

        txtNum1Div.setText(String.valueOf(a));
        txtNum2Div.setText(String.valueOf(b));
    }
}


