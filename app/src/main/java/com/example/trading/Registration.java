package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {
    EditText username, email,password,re_password;
    Spinner user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Intent intent = getIntent();

        Spinner spin = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.user_types));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
        username = (EditText)findViewById(R.id.editText1);
        email = (EditText)findViewById(R.id.editText2);
        password = (EditText)findViewById(R.id.editText3);
        re_password = (EditText)findViewById(R.id.editText4);
        user_type = (Spinner)findViewById(R.id.spinner);
        Button reg = (Button)findViewById(R.id.button1);
        reg.setOnClickListener(register_user);
    }
    View.OnClickListener register_user = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            if(TextUtils.isEmpty(username.getText().toString()))
            {
                username.setError("Please Enter Username");
                return;
            }
            if(TextUtils.isEmpty(email.getText().toString()))
            {
                email.setError("Please Enter Email");
                return;
            }
            if(TextUtils.isEmpty(password.getText().toString()))
            {
                password.setError("Please Enter Password");
                return;
            }
            if(TextUtils.isEmpty(re_password.getText().toString()))
            {
                password.setError("Please Repeat Password");
                return;
            }
            if((password.getText().toString()).equals(re_password.getText().toString()))
            {
                register_newUser();
            }
            else
            {

                ((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull Register");
                ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);
            }
        }
    };

    void register_newUser(){
        String uname = username.getText().toString();
        String uemail = email.getText().toString();
        String upass = password.getText().toString();
        String utype = user_type.getSelectedItem().toString();
        String r_file = "register.php";
        String r_url = "?username="+uname+"&email="+uemail+"&password="+upass+"&user_type="+utype;
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(Registration.this);

        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String rsp) {
                        try {
                            JSONObject jsonObject = new JSONObject(rsp);
                            String status = jsonObject.getString("response");

                            if(!status.equals("OK"))
                            {
                                ((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull Register");
                                ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);
                            }
                            else
                            {

                                Intent back_to_login = new Intent(Registration.this,MainActivity.class);
                                startActivity(back_to_login);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull Register");
                ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);
            }
        });
        queue.add(stringRequest);
    }
}
