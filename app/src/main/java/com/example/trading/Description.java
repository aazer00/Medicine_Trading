package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Description extends AppCompatActivity {
    String owner;
    String owner_id;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Intent gelen = getIntent();
        String med_name = gelen.getStringExtra("med_name");
        owner = gelen.getStringExtra("owner_name");
        user_id = gelen.getStringExtra("user_id");
        owner_id = gelen.getStringExtra("owner_id");
        String rating = gelen.getStringExtra("rating");

        ((TextView)findViewById(R.id.textView1)).setText(med_name);
        ((TextView)findViewById(R.id.textView3)).setText(owner);
        ((TextView)findViewById(R.id.textView5)).setText(rating);
        ((Button)findViewById(R.id.button1)).setOnClickListener(func_rate);
        ((Button)findViewById(R.id.button2)).setOnClickListener(start_conver);
        String r_file = "getDescription.php";
        String r_url = "?user_id="+user_id+"&owner="+owner+"&owner_id="+owner_id+"&med_name="+med_name;
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(Description.this);

        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String rsp) {
                        try {
                            JSONObject rsp_json = new JSONObject(rsp);
                            String formula = rsp_json.getString("formula");
                            String dose = rsp_json.getString("dose");
                            String phone = rsp_json.getString("phone");
                            String icaze = rsp_json.getString("icaze");
                            //owner_id = rsp_json.getString("owner_id");
                            ((TextView)findViewById(R.id.textView2)).setText(formula);
                            ((TextView)findViewById(R.id.textView4)).setText(dose);
                            ((TextView)findViewById(R.id.textView11)).setText(phone);
                            if(icaze.equals("0"))
                            {
                                ((Button)findViewById(R.id.button1)).setEnabled(false);
                            }
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

    View.OnClickListener func_rate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((Button)findViewById(R.id.button1)).setEnabled(false);
            float rat = ((RatingBar)findViewById(R.id.ratingBar1)).getRating();
            String s_rat = Float.toString(rat);
            Log.i("rate",s_rat);

            String r_file = "rate.php";
            String r_url = "?owner="+owner+"&rate="+s_rat+"&user_id="+user_id+"&owner_id="+owner_id;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(Description.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {
                            try {
                                JSONObject rsp_json = new JSONObject(rsp);
                                String status = rsp_json.getString("status");
                                Log.i("db",status);
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
    };
    View.OnClickListener start_conver = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String r_file = "start_conversation.php";
            String r_url = "?user_id="+user_id+"&owner_id="+owner_id;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(Description.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {

                                String room = rsp;
                                Intent intent = new Intent(Description.this, Coversation.class);
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
