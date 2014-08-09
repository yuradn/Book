package com.test.yura.jul31;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by yura on 31.07.14.
 */
public class Show extends Activity implements View.OnClickListener {

    TextView firstName, lastName;
    String ln, fn;
    //Person pp;
    Intent intent;
    Button btn,btnConn, btnDisconn, btnCreate, btnLoad;
    TextView txtOut;
    boolean connecting;
    Person_DAO_Svr  pDaoSrv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);

        intent=getIntent();
//
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        btnConn = (Button) findViewById(R.id.btnConnect);
        btnConn.setOnClickListener(this);

        btnCreate = (Button) findViewById(R.id.btnSend);
        btnCreate.setOnClickListener(this);

        txtOut = (TextView) findViewById(R.id.textView);

//
        //pp.setFirstName("test");
        //pp.setLastName("test2");
        fn=intent.getStringExtra("firstName");
        ln=intent.getStringExtra("lastName");
//
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
//
        firstName.setText(fn);
        lastName.setText(ln);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.btn:
            Intent intent = new Intent(Show.this, Main.class);
            startActivity(intent);
        break;
        case R.id.btnConnect:
            pDaoSrv = new Person_DAO_Svr();
            //connecting = pDaoSrv.connect();
            Thread cThread = new Thread(new Person_DAO_Svr());
            cThread.start();
            if (connecting) txtOut.setText("Connect!");
            else txtOut.setText("Error connection!");

        break;
        case  R.id.btnSend:
            if (connecting) txtOut.setText("Sending...");
            else {txtOut.setText("Connect not enought..."); break;}
            Person pp = new Person("Vasja","Maljuk",18);
            pDaoSrv.create(pp);

        break;
        }
    }
}
