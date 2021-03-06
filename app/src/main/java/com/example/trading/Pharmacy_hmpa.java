package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Pharmacy_hmpa extends AppCompatActivity {
    String pharmacy_id;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_hmpa);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        Intent gelen = getIntent();
        user_id = gelen.getStringExtra("com.example.trading.user_id");
        ((Button)findViewById(R.id.button1)).setOnClickListener(edit_inform);
        ((Button)findViewById(R.id.button2)).setOnClickListener(save_inform);
        ((Button)findViewById(R.id.button3)).setOnClickListener(changePass);
        ((Button)findViewById(R.id.button5)).setOnClickListener(search_items);
        ((Button)findViewById(R.id.button6)).setOnClickListener(go_chats);

        String r_file = "pharmacy_hmp.php";
        String r_url = "?user_id="+user_id;
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(Pharmacy_hmpa.this);

        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    String username;
                    String email;
                    String user_type;
                    String phar_id;
                    String phar_name;
                    String phar_number;
                    String status;
                    @Override
                    public void onResponse(String rsp) {
                        try {
                            JSONObject rsp_json = new JSONObject(rsp);
                            username = rsp_json.getString("username");
                            email = rsp_json.getString("email");
                            user_type = rsp_json.getString("user_type");
                            phar_id = rsp_json.getString("phar_id");
                            phar_name = rsp_json.getString("phar_name");
                            status = rsp_json.getString("response");
                            phar_number = rsp_json.getString("phar_number");
                            pharmacy_id = phar_id;

                            if(status.equals("OK"))
                            {
                                ((EditText)findViewById(R.id.editText1)).setText(username);
                                ((EditText)findViewById(R.id.editText2)).setText(user_type);
                                ((EditText)findViewById(R.id.editText3)).setText(phar_name);
                                ((EditText)findViewById(R.id.editText4)).setText(phar_number);
                                ((EditText)findViewById(R.id.editText5)).setText(email);
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

    View.OnClickListener edit_inform = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((Button)findViewById(R.id.button1)).setEnabled(false);
            ((Button)findViewById(R.id.button2)).setEnabled(true);
            ((Button)findViewById(R.id.button3)).setEnabled(false);
            ((Button)findViewById(R.id.button4)).setEnabled(false);
            ((Button)findViewById(R.id.button5)).setEnabled(false);


            ((EditText)findViewById(R.id.editText3)).setEnabled(true);
            ((EditText)findViewById(R.id.editText4)).setEnabled(true);

            ((EditText)findViewById(R.id.editText3)).setText("");
            ((EditText)findViewById(R.id.editText4)).setText("");

        }
    };
    View.OnClickListener save_inform = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((Button)findViewById(R.id.button1)).setEnabled(true);
            ((Button)findViewById(R.id.button2)).setEnabled(false);
            ((Button)findViewById(R.id.button3)).setEnabled(true);
            ((Button)findViewById(R.id.button4)).setEnabled(true);
            ((Button)findViewById(R.id.button5)).setEnabled(true);


            ((EditText)findViewById(R.id.editText3)).setEnabled(false);
            ((EditText)findViewById(R.id.editText4)).setEnabled(false);

            saveToDatabase();
        }
    };

    public void saveToDatabase(){

        String r_file = "phar_changeProfile.php";
        String r_url = "?phar_id="+pharmacy_id+"&phar_name="+((EditText)findViewById(R.id.editText3)).getText().toString()
                +"&phar_number="+((EditText)findViewById(R.id.editText4)).getText().toString();
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(Pharmacy_hmpa.this);

        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    String status;
                    @Override
                    public void onResponse(String rsp) {
                        try {
                            JSONObject rsp_json = new JSONObject(rsp);
                            status = rsp_json.getString("response");
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

    View.OnClickListener changePass = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pharmacy_hmpa.this, ChangePassword.class);
            intent.putExtra("com.example.trading.user_id",user_id);
            startActivity(intent);
        }
    };
    View.OnClickListener search_items = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pharmacy_hmpa.this, Search_page.class);
            intent.putExtra("user_id",user_id);
            intent.putExtra("type","Pharmacy");
            startActivity(intent);
        }
    };
    View.OnClickListener go_chats = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pharmacy_hmpa.this, Chats.class);
            intent.putExtra("user_id",user_id);
            startActivity(intent);
        }
    };

}
