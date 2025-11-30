package com.example.kidsmath;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kidsmath.api.ApiClient;

import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {

    TextView txtSuma, txtResta, txtMulti, txtDiv, txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtSuma = findViewById(R.id.txtScoreSum);
        txtResta = findViewById(R.id.txtScoreSub);
        txtMulti = findViewById(R.id.txtScoreMulti);
        txtDiv = findViewById(R.id.txtScoreDiv);
        txtTotal = findViewById(R.id.txtTotalScore);

        loadScoresFromAPI();
    }

    private void loadScoresFromAPI() {

        ApiClient api = new ApiClient(this);

        api.getScores(new ApiClient.ApiResponse() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    try {
                        JSONObject json = new JSONObject(response);

                        txtSuma.setText("Puntaje: " + json.getInt("sumas"));
                        txtResta.setText("Puntaje: " + json.getInt("restas"));
                        txtMulti.setText("Puntaje: " + json.getInt("multiplicaciones"));
                        txtDiv.setText("Puntaje: " + json.getInt("divisiones"));
                        txtTotal.setText("Puntos Totales: " + json.getInt("total"));

                    } catch (Exception e) {
                        Toast.makeText(ScoreActivity.this, "Error JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() ->
                        Toast.makeText(ScoreActivity.this, "Error API: " + error, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}

