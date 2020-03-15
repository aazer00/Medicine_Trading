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

public class Forgot_next extends AppCompatActivity {
    String pin_code = "";
    String pass = "";
    String email ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_next);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Intent intent = getIntent();
        pin_code = intent.getStringExtra("pin");
        pass = intent.getStringExtra("pass");
        email = intent.getStringExtra("email");
        ((Button)findViewById(R.id.button1)).setOnClickListener(check);
    }

    View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String yazilan=((EditText)findViewById(R.id.editText1)).getText().toString();
            if(yazilan.equals(pin_code))
            {


                String r_file = "forgotChange.php";
                String r_url = "?email="+email+"&pass="+pass;
                String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
                RequestQueue queue = Volley.newRequestQueue(Forgot_next.this);

                String full_url = mainUrl+r_file+r_url;
                Log.i("db",full_url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String rsp) {
                                ((EditText)findViewById(R.id.editText2)).setText("Succesfull");
                                Intent intent = new Intent(Forgot_next.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(stringRequest);

            }
            else
            {
                ((EditText)findViewById(R.id.editText1)).setText("Wrong PIN");
            }
        }
    };
}
