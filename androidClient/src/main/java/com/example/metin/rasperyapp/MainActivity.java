package com.example.metin.rasperyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    private EditText etMsg,etIP,etPort;
    private Button button;
    double karesi;
    private Socket skt=null;
    private static DataInputStream okuyucu;
    ProgressDialog pd=null;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etIP = (EditText)findViewById(R.id.editText);
        etPort = (EditText)findViewById(R.id.editText2);
        button = (Button)findViewById(R.id.button);
        tv=(TextView) findViewById(R.id.tv);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService (Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo ();
        String ssid  = info.getSSID();
        Toast.makeText(getApplicationContext(),ssid,Toast.LENGTH_SHORT).show();
        tv.setText(ssid);


    }

    public void send_textClicked(View view)  {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Yükleniyor");
        pd.setIcon(R.drawable.progressimage);
        pd.show();
        Intent intent = new Intent(MainActivity.this,RaspControlActivity.class);
        intent.putExtra("ip",etIP.getText().toString());
        intent.putExtra("port",etPort.getText().toString());
        startActivity(intent);
    }
}