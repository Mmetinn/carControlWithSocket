package com.example.metin.rasperyapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.Buffer;

public class RaspControlActivity  extends AppCompatActivity {
    Button sagBtn,solBtn,ileriBtn,geriBtn;
    double karesi;
    private Socket skt=null;
    //private static DataInputStream okuyucu;
    TextView tx;
    ProgressDialog pd=null;
    //DataOutputStream yazıcı;
    static ObjectOutputStream yazıcı;
    static ObjectInputStream okuyucu;
    String ip;
    int port;
    String ileri,geri,sag,sol,dur,komut,vites;
    int speed;
    JSONObject item=new JSONObject();
    String gelenJSON="";
    final String kimlik="android";
    SeekBar pwm;
    ImageView iv;
    int sayac=0;
    SeekBar sekPwm;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasp_control);
        ileriBtn = (Button)findViewById(R.id.ileri);
        geriBtn = (Button)findViewById(R.id.geri);
        solBtn = (Button)findViewById(R.id.sol);
        sagBtn = (Button)findViewById(R.id.sag);
        iv=(ImageView)findViewById(R.id.imageView);
        sekPwm=(SeekBar)findViewById(R.id.pwm);

        //tx=(TextView)findViewById(R.id.textView);
        ip = getIntent().getStringExtra("ip");
        port = Integer.parseInt(getIntent().getStringExtra("port"));

        sekPwm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if(speed <= 25) {
                    vites="1";}
                else if(speed <= 50) {
                    vites="2";}
                else if(speed <= 75) {
                    vites="3";}
                else if(speed <= 100) {
                    vites="4";}
                else if(speed <= 125) {
                    vites="5";}
                else if(speed <= 150) {
                    vites="6";}
                else if(speed <= 175) {
                    vites="7";}
                else if(speed <= 200) {
                    vites="8";}
                else if(speed <= 250) {
                    vites="9";}


                //bl.sendData(String.valueOf(speedLetter));

            }

        });

        ileriBtn.setOnTouchListener(new View.OnTouchListener()  {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)  {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        iv.setImageResource(R.drawable.yukari);
                        ileri="1";
                        geri="0";
                        sag="0";
                        sol="0";
                        dur="0";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        iv.setImageResource(R.drawable.dur);
                        ileri="0";
                        geri="0";
                        sag="0";
                        sol="0";
                        dur="1";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                RaspControlActivity.asyn as = new asyn();
                as.execute();
                return true;
            }
        });

        geriBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        iv.setImageResource(R.drawable.asagi);
                        ileri="0";
                        geri="1";
                        sag="0";
                        sol="0";
                        dur="0";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        iv.setImageResource(R.drawable.dur);
                        ileri="0";
                        geri="0";
                        sag="0";
                        sol="0";
                        dur="1";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                RaspControlActivity.asyn as = new asyn();
                as.execute();
                return true;
            }
        });

        sagBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        iv.setImageResource(R.drawable.saga);
                        ileri="0";
                        geri="0";
                        sag="1";
                        sol="0";
                        dur="0";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        iv.setImageResource(R.drawable.dur);
                        ileri="0";
                        geri="0";
                        sag="0";
                        sol="0";
                        dur="1";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                RaspControlActivity.asyn as = new asyn();
                as.execute();
                return true;
            }
        });

        solBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        iv.setImageResource(R.drawable.sola);
                        ileri="0";
                        geri="0";
                        sag="0";
                        sol="1";
                        dur="0";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        iv.setImageResource(R.drawable.dur);
                        ileri="0";
                        geri="0";
                        sag="0";
                        sol="0";
                        dur="1";
                        try {
                            item.put("dur",dur);
                            item.put("ileri",ileri);
                            item.put("geri",geri);
                            item.put("sag",sag);
                            item.put("sol",sol);
                            item.put("kimlik",kimlik);
                            item.put("vites",vites);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                RaspControlActivity.asyn as = new asyn();
                as.execute();
                return true;
            }
        });
    }
    class asyn extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            try {
                skt = new Socket(ip,port);
                yazıcı = new ObjectOutputStream(skt.getOutputStream());
                okuyucu = new ObjectInputStream(skt.getInputStream());
                yazıcı.writeObject(item.toString());
                yazıcı.flush();
                //gelenJSON = (String)okuyucu.readObject();
                skt.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gelenJSON;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject jobj= null;
            try {
                jobj = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(),"obje::"+jobj,Toast.LENGTH_SHORT).show();
        }
    }
}