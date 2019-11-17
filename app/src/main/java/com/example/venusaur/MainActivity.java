package com.example.venusaur;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView mTitle;

    TextView mDescription;

    TextView mDateLabel0, mDateLabel1;

    TextView mTelop0, mTelop1;

    NetworkImageView mImage0, mImage1;

    ImageLoader mImageLoader;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.title);
        mDescription = findViewById(R.id.description);
        mDateLabel0 = findViewById(R.id.dateLabel0);
        mDateLabel1 = findViewById(R.id.dateLabel1);
        mTelop0 = findViewById(R.id.telop0);
        mTelop1 = findViewById(R.id.telop1);
        mImage0 = findViewById(R.id.image0);
        mImage1 = findViewById(R.id.image1);
        mImageLoader = MySingleton.getInstance(this).getmImageLoader();

        String url = "http://weather.livedoor.com/forecast/webservice/json/v1?city=130010";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTitle.setText(response.getString("title"));
                            mDescription.setText(response.getJSONObject("description").getString("text"));
                            mDateLabel0.setText(response.getJSONArray("forecasts").getJSONObject(0).getString("dateLabel"));
                            mDateLabel1.setText(response.getJSONArray("forecasts").getJSONObject(1).getString("dateLabel"));
                            mTelop0.setText(response.getJSONArray("forecasts").getJSONObject(0).getString("telop"));
                            mTelop1.setText(response.getJSONArray("forecasts").getJSONObject(1).getString("telop"));
                            String url0 = response.getJSONArray("forecasts").getJSONObject(0).getJSONObject("image").getString("url");
                            String url1 = response.getJSONArray("forecasts").getJSONObject(1).getJSONObject("image").getString("url");
                            mImage0.setImageUrl(url0, mImageLoader);
                            mImage1.setImageUrl(url1, mImageLoader);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        );
        MySingleton.getInstance(this).addToRequestQue(jsonRequest);


    }
}
