package com.example.trading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Coversation extends AppCompatActivity {
    String user_id;
    String room;
    TextView tv;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coversation);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        room = intent.getStringExtra("room");
        ((TextView)findViewById(R.id.textView1)).setMovementMethod(new ScrollingMovementMethod());
        ((Button)findViewById(R.id.button1)).setOnClickListener(send_mess);
        tv= (TextView) findViewById(R.id.textView1);

        timer = new Timer();
            //Set the schedule function
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {

                                          runOnUiThread(new Runnable(){

                                              @Override
                                              public void run(){
                                                  get_messages();
                                              }
                                          });

                                      }
                                  },
                0, 3000);
    }
    View.OnClickListener send_mess = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String mess = ((EditText)findViewById(R.id.editText1)).getText().toString();
            ((EditText)findViewById(R.id.editText1)).setText("");
            if(!(mess.trim()).equals(""))
            ((TextView)findViewById(R.id.textView1)).setText("(You) "+mess+"\n"+
                    ((TextView)findViewById(R.id.textView1)).getText().toString());
            String r_file = "sendMessage.php";
            String r_url = "?sender="+user_id+"&room="+room+"&mess="+mess;
            String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
            RequestQueue queue = Volley.newRequestQueue(Coversation.this);

            String full_url = mainUrl+r_file+r_url;
            Log.i("db",full_url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String rsp) {

                            ((TextView)findViewById(R.id.tx_respond)).setText(rsp);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ((TextView)findViewById(R.id.tx_respond)).setText("response_error");
                }
            });
            queue.add(stringRequest);
        }
    };
    public void get_messages(){
        String r_file = "receiveMessage.php";
        String r_url = "?receiver="+user_id+"&room="+room;
        String mainUrl = "http://halted-refrigerator.000webhostapp.com/medical/";
        RequestQueue queue = Volley.newRequestQueue(Coversation.this);

        String full_url = mainUrl+r_file+r_url;
        Log.i("db",full_url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, full_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String rsp) {
                        String[] results = rsp.split(":");
                        for(int i =0;i<results.length;i++)
                        {
                            if(!results[i].equals(""))
                            ((TextView)findViewById(R.id.textView1)).append(results[i]+"\n");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView)findViewById(R.id.tx_respond)).setText("response_error");
            }
        });
        queue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer = null;
    }
}
