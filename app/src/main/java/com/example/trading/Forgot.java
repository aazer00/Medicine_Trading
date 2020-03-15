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

import java.util.Random;

public class Forgot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Intent intent = getIntent();
        ((Button)findViewById(R.id.button1)).setOnClickListener(next);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email=((EditText)findViewById(R.id.editText1)).getText().toString();
            final String password=((EditText)findViewById(R.id.editText2)).getText().toString();
            Random random = new Random();
            final int code = random.nextInt(700)+100;
            String r_file = "forgot.php";
            String r_url = "?email="+email+"&pin="+ String.valueOf(code);
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(Forgot.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {
                            ((EditText)findViewById(R.id.editText3)).setText(rsp);
                            Intent intent = new Intent(Forgot.this, Forgot_next.class);
                            intent.putExtra("pass",password);
                            intent.putExtra("pin",String.valueOf(code));
                            intent.putExtra("email",((EditText)findViewById(R.id.editText1)).getText().toString());
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
