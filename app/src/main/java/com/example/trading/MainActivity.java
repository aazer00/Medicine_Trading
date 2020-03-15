package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = (Button)findViewById(R.id.button1);
        Button btn_register = (Button)findViewById(R.id.button2);
        btn_login.setOnClickListener(funct_login);
        btn_register.setOnClickListener(funct_register);
        ((Button)findViewById(R.id.button_hs)).setOnClickListener(hide_show);
        ((Button)findViewById(R.id.button3)).setOnClickListener(forgot);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );



    }

    View.OnClickListener funct_login = new View.OnClickListener(){
        @Override
        public void onClick(View view)
        {

            String username = ((EditText)findViewById(R.id.editText1)).getText().toString();
            String password = ((EditText)findViewById(R.id.editText2)).getText().toString();
            final TextView tx = (TextView)findViewById(R.id.textView3);
            String r_file = "login.php";
            String r_url = "?username="+username+"&password="+password;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        String status;
                        String user_id;
                        String user_type;
                        @Override
                        public void onResponse(String rsp) {
                            try {
                                JSONObject jsonObject = new JSONObject(rsp);
                                status =jsonObject.getString("response");
                                user_id=jsonObject.getString("user_id");
                                user_type=jsonObject.getString("user_type");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("db",status);
                            if(status.equals("OK"))
                            {

                                if(user_type.equals("Consumer"))
                                {
                                    Log.i("db",user_type);
                                    Intent intent = new Intent(MainActivity.this, Consumer_hmpa.class);
                                    intent.putExtra("com.example.trading.user_id",user_id);
                                    startActivity(intent);
                                }
                                if(user_type.equals("Doctor"))
                                {
                                    Log.i("db",user_type);
                                    Intent intent = new Intent(MainActivity.this, Doctor_hmpa.class);
                                    intent.putExtra("com.example.trading.user_id",user_id);
                                    startActivity(intent);
                                }
                                if(user_type.equals("Pharmacy"))
                                {
                                    Log.i("db",user_type);
                                    Intent intent = new Intent(MainActivity.this, Pharmacy_hmpa.class);
                                    intent.putExtra("com.example.trading.user_id",user_id);
                                    startActivity(intent);
                                }
                                if(user_type.equals("Supplier"))
                                {
                                    Log.i("db",user_type);
                                    Intent intent = new Intent(MainActivity.this, Supplier_hmpa.class);
                                    intent.putExtra("com.example.trading.user_id",user_id);
                                    startActivity(intent);
                                }


                            }
                            else
                            {
                                Log.i("db",status);
                                ((TextView)findViewById(R.id.tx_respond)).setText("Unsuccesfull Login");
                                ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);
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

    View.OnClickListener funct_register = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        }
    };
    View.OnClickListener hide_show = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(((Button)findViewById(R.id.button_hs)).getText().toString().equals("Show")){
                ((EditText)findViewById(R.id.editText2)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ((Button)findViewById(R.id.button_hs)).setText("Hide");
            } else{
                ((EditText)findViewById(R.id.editText2)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                ((Button)findViewById(R.id.button_hs)).setText("Show");
            }
        }
    };
    View.OnClickListener forgot = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, Forgot.class);
            //Intent intent = new Intent(MainActivity.this, addLocation.class);
            startActivity(intent);
        }
    };


}
