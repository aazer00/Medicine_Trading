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

public class IDconfimation extends AppCompatActivity {
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idconfimation);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        ((Button)findViewById(R.id.button1)).setOnClickListener(confirmation);
    }
    View.OnClickListener confirmation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String id = ((EditText)findViewById(R.id.editText1)).getText().toString();
            String r_file = "idConfirmation.php";

            String r_url = "?user_id="+user_id+"&id_con="+id;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(IDconfimation.this);

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

                                    ((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull Attempt");
                                    ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);

                                }
                                else
                                {
                                    Intent back_intent = new Intent(IDconfimation.this,Doctor_hmpa.class);
                                    back_intent.putExtra("com.example.trading.user_id",user_id);
                                    startActivity(back_intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull Attempt");
                                ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //((TextView)findViewById(R.id.tx_respond)).setText("Unsuccessfull Attempt");
                    ((TextView)findViewById(R.id.tx_respond)).setTextColor(Color.RED);
                }
            });
            queue.add(stringRequest);
        }
    };
}
