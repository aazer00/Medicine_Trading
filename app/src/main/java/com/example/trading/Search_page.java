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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Search_page extends AppCompatActivity {
    String[] separated;
    String user_id;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        type = intent.getStringExtra("type");

        ((Button)findViewById(R.id.button1)).setOnClickListener(search_items);
    }

    View.OnClickListener search_items = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((LinearLayout)findViewById(R.id.scroll_items)).removeAllViews();
            String r_file="";
            String text = ((EditText)findViewById(R.id.editText3)).getText().toString();
            if(type.equals("Consumer")||type.equals("Doctor"))
            {
                r_file = "consumerSearch.php";
            }
            if(type.equals("Pharmacy"))
            {
                r_file = "pharmacySearch.php";
            }
            if(type.equals("Supplier"))
            {
                r_file = "supplierSearch.php";
            }

            String r_url = "?text="+text;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(Search_page.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {
                            //((TextView)findViewById(R.id.textView)).setText(rsp);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
                                    String[] medicine = separated[i].split(":");
                                    Button tv = new Button(Search_page.this);
                                    tv.setText(medicine[0]);
                                    tv.setLayoutParams(params);
                                    tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                    tv.setBackgroundColor(Color.WHITE);
                                    tv.setWidth(500);
                                    if(type.equals("Supplier"))
                                    {
                                        tv.setOnClickListener(go_doc);
                                    }
                                    else
                                    {
                                        tv.setOnClickListener(go_desc);
                                    }

                                    tv.setId(i);

                                    Button tv2 = new Button(Search_page.this);
                                    tv2.setText(medicine[2]);
                                    tv2.setLayoutParams(params);
                                    tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    tv2.setBackgroundColor(Color.WHITE);

                                    //tv.setEnabled(false);
                                    //((LinearLayout)findViewById(R.id.scroll_items)).addView(tv);

                                    View v = new View(Search_page.this);
                                    v.setLayoutParams(params);
                                    v.setMinimumHeight(1);
                                    v.setBackgroundColor(Color.BLACK);
                                    //((LinearLayout)findViewById(R.id.scroll_items)).addView(v);

                                    LinearLayout l = new LinearLayout(Search_page.this);
                                    //l.setLayoutParams(params);
                                    l.setOrientation(LinearLayout.HORIZONTAL);
                                    l.addView(tv);
                                    l.addView(tv2);
                                    ((LinearLayout)findViewById(R.id.scroll_items)).addView(l);
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
    };

    View.OnClickListener go_desc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = ((Button)view).getId();
            String owner = (separated[id].split(":"))[1];
            String rating = (separated[id].split(":"))[2];
            String owner_id = (separated[id].split(":"))[3];
            Intent intent = new Intent(Search_page.this, Description.class);
            intent.putExtra("med_name",((Button)view).getText().toString());
            intent.putExtra("owner_name",owner);
            intent.putExtra("rating",rating);
            intent.putExtra("user_id",user_id);
            intent.putExtra("owner_id",owner_id);
            startActivity(intent);
        }
    };
    View.OnClickListener go_doc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = ((Button)view).getId();

            String owner_id = (separated[id].split(":"))[2];
            Intent intent = new Intent(Search_page.this, DoctorProfile.class);
            intent.putExtra("user_id",user_id);
            intent.putExtra("second_id",owner_id);
            startActivity(intent);
        }
    };
}
