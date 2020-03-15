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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        user_id =intent.getStringExtra("com.example.trading.user_id");
        ((Button)findViewById(R.id.button1)).setOnClickListener(change_password);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


    }
    View.OnClickListener change_password = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String old_pass = ((EditText)findViewById(R.id.editText1)).getText().toString();
            String new_pass = ((EditText)findViewById(R.id.editText2)).getText().toString();
            String re_pass = ((EditText)findViewById(R.id.editText3)).getText().toString();
            String r_file = "changePassword.php";
            if(!new_pass.equals(re_pass))
            {
                r_file = "";
            }

            String r_url = "?user_id="+user_id+"&old_pass="+old_pass+"&new_pass="+new_pass;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(rsp);
                                String status =jsonObject.getString("response");
                                if(!status.equals("OK"))
                                {
                                    ((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull");
                                    ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);

                                }
                                else
                                {
                                   finish();
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
    };
}
