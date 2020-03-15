package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DoctorProfile extends AppCompatActivity {
    String user_id,second_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Intent gelen = getIntent();

        user_id = gelen.getStringExtra("user_id");
        second_id = gelen.getStringExtra("second_id");

        ((Button)findViewById(R.id.button2)).setOnClickListener(start_conver);
        String r_file = "getDoc.php";
        String r_url = "?user_id="+user_id+"&second_id="+second_id;
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(DoctorProfile.this);

        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String rsp) {
                        try {
                            JSONObject rsp_json = new JSONObject(rsp);
                            String name = rsp_json.getString("name");
                            String sahe = rsp_json.getString("sahe");
                            String phone = rsp_json.getString("phone");
                            ((TextView)findViewById(R.id.textView1)).setText(name);
                            ((TextView)findViewById(R.id.textView3)).setText(sahe);
                            ((TextView)findViewById(R.id.textView4)).setText(phone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    View.OnClickListener start_conver = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String r_file = "start_conversation.php";
            String r_url = "?user_id="+user_id+"&owner_id="+second_id;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(DoctorProfile.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {

                            String room = rsp;
                            Intent intent = new Intent(DoctorProfile.this, Coversation.class);
                            intent.putExtra("user_id",user_id);
                            intent.putExtra("room",room);
                            startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);

        }
    };
}
