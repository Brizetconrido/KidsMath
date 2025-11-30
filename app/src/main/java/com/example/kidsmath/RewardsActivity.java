package com.example.kidsmath;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidsmath.api.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class RewardsActivity extends AppCompatActivity {

    LinearLayout rewardsContainer;
    TextView txtCurrentPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        rewardsContainer = findViewById(R.id.rewardsContainer);
        txtCurrentPoints = findViewById(R.id.txtCurrentPoints);

        loadRewardsFromAPI();
    }

    private void loadRewardsFromAPI() {

        ApiClient api = new ApiClient(this);

        api.getScores(new ApiClient.ApiResponse() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    try {
                        JSONObject json = new JSONObject(response);

                        // Mostrar puntos totales
                        int total = json.getInt("total");
                        txtCurrentPoints.setText("Puntos: " + total);

                        // Mostrar recompensas
                        JSONArray rewards = json.getJSONArray("rewards");

                        rewardsContainer.removeAllViews();

                        for (int i = 0; i < rewards.length(); i++) {
                            JSONObject rw = rewards.getJSONObject(i);

                            TextView txt = new TextView(RewardsActivity.this);
                            txt.setText(rw.getString("name") + " - " + rw.getString("description"));
                            txt.setTextSize(22f);
                            txt.setPadding(25, 25, 25, 25);

                            rewardsContainer.addView(txt);
                        }

                    } catch (Exception e) {
                        Toast.makeText(RewardsActivity.this, "Error JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() ->
                        Toast.makeText(RewardsActivity.this, "Error API: " + error, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
