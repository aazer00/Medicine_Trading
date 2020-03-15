package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Chats extends AppCompatActivity {
    String user_id;
    String room;
    String[] separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        String r_file="getChats.php";
        String r_url = "?user_id="+user_id;
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(Chats.this);


        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String rsp) {
                        //((TextView)findViewById(R.id.textView)).setText(rsp);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        Log.i("db",rsp);
                        if(rsp.equals(""))
                        {
                            Log.i("db","no result");
                        }
                        else
                        {
                            separated = rsp.split(",");
                            //((TextView)findViewById(R.id.textView)).setText(separated[1]);
                            for(int i =0;i<separated.length;i++)
                            {
                                String[] conv = separated[i].split(":");
                                Button tv = new Button(Chats.this);
                                tv.setText(conv[0]);
                                tv.setLayoutParams(params);
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                tv.setBackgroundColor(Color.WHITE);
                                tv.setOnClickListener(start_conver);
                                tv.setId(i);


                                //tv.setEnabled(false);
                                //((LinearLayout)findViewById(R.id.scroll_items)).addView(tv);

                                View v = new View(Chats.this);
                                v.setLayoutParams(params);
                                v.setMinimumHeight(1);
                                v.setBackgroundColor(Color.BLACK);
                                //((LinearLayout)findViewById(R.id.scroll_items)).addView(v);

                                ((LinearLayout)findViewById(R.id.scroll_items)).addView(tv);
                                ((LinearLayout)findViewById(R.id.scroll_items)).addView(v);

                            }
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
            int id = ((Button)view).getId();
            String room = (separated[id].split(":"))[1];

            Intent intent = new Intent(Chats.this, Coversation.class);
            intent.putExtra("user_id",user_id);
            intent.putExtra("room",room);
            startActivity(intent);


        }
    };
}
