package com.example.kidsmath.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    // URL correcta
    private static final String BASE_URL = "http://10.0.2.2/api/api.php";

    private final RequestQueue queue;

    public ApiClient(Context context) {
        queue = Volley.newRequestQueue(context);
    }


    public void sendScore(String name, int points, String game, ApiResponse listener) {

        String url = BASE_URL + "?action=add_score";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                listener::onSuccess,
                error -> listener.onError(error.toString())
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("points", String.valueOf(points));
                params.put("game", game);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1f));
        queue.add(request);
    }

    // Obtener puntajes
    public void getScores(ApiResponse listener) {
        String url = BASE_URL + "?action=get_scores";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                listener::onSuccess,
                error -> listener.onError(error.toString()));

        queue.add(request);
    }

    public void updateProfile(String name, int age, ApiResponse listener) {

        String url = BASE_URL + "?action=update_profile";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                listener::onSuccess,
                error -> listener.onError(error.toString())
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("age", String.valueOf(age));
                return map;
            }
        };

        queue.add(request);
    }

    public void getProfile(ApiResponse listener) {
        String url = BASE_URL + "?action=get_profile";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                listener::onSuccess,
                error -> listener.onError(error.toString()));

        queue.add(request);
    }

    public void getRewards(ApiResponse listener) {
        String url = BASE_URL + "?action=get_rewards";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                listener::onSuccess,
                error -> listener.onError(error.toString()));

        queue.add(request);
    }

    public interface ApiResponse {
        void onSuccess(String response);
        void onError(String error);
    }
}
