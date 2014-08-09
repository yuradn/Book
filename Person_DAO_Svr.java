package com.test.yura.jul31;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * Created by yura on 04.08.14.
 */
public class Person_DAO_Svr implements PersonDAO, Runnable{

    Socket cs = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    Gson gs = null;

    @Override
    public void run() {
        Socket cs = null;
        try {
            Log.d("Connect:", "Execute ...");
            cs = new Socket("localhost", 1096);
            cs.setSoTimeout(100);
            Log.d("Connect:", "Client connected => " + cs.getLocalPort());
            DataInputStream dis = new DataInputStream(cs.getInputStream());
            DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
            //InputStream dis = cs.getInputStream();
            //OutputStream dos = cs.getOutputStream();


            Log.d("Connect:", "Client connected => " + cs.getLocalPort());
        } catch (UnknownHostException e) {
            Log.e("Connect", "" + e);
        } catch (IOException e) {
            Log.e("Connect", "" + e);
        }
    }


    public class Connect extends AsyncTask<Void, Void, Void> implements Runnable{

        String dstAdress = null;
        int dstPort;


        Connect (String addr, int port) {
            dstAdress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Socket cs = null;
            try {
                Log.d("Connect:", "Execute ...");
                cs = new Socket("127.0.0.1", 1096);
                cs.setSoTimeout(100);
                Log.d("Connect:", "Client connected => " + cs.getLocalPort());
                //DataInputStream dis = new DataInputStream(cs.getInputStream());
                //DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
                InputStream dis = cs.getInputStream();
                OutputStream dos = cs.getOutputStream();


                Log.d("Connect:", "Client connected => " + cs.getLocalPort());
            } catch (UnknownHostException e) {
                Log.e("Connect", "" + e);
            } catch (IOException e) {
                Log.e("Connect", "" + e);
            }
            return  null;
        }

        @Override
        public void run() {
            Socket cs = null;
            try {
                Log.d("Connect:", "Execute ...");
                cs = new Socket("127.0.0.1", 1096);
                cs.setSoTimeout(100);
                Log.d("Connect:", "Client connected => " + cs.getLocalPort());
                //DataInputStream dis = new DataInputStream(cs.getInputStream());
                //DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
                InputStream dis = cs.getInputStream();
                OutputStream dos = cs.getOutputStream();


                Log.d("Connect:", "Client connected => " + cs.getLocalPort());
            } catch (UnknownHostException e) {
                Log.e("Connect", "" + e);
            } catch (IOException e) {
                Log.e("Connect", "" + e);
            }
        }
    }

    public boolean connect() {

        gs = new Gson();
        //Log.d("Connect:","Client connected => " + cs.getLocalPort());
        Connect conn = new Connect("localhost", 1096);
        conn.execute();
        if (cs!=null) return true; else return false;
    }

    public void disconnect()  {
        try {
            dos.writeUTF("disconnect");
            dos.flush();
            cs.close();
        }
        catch (UnknownHostException  e) {
            Log.e("Disconnect:","Error disconnect.");
        }
        catch (IOException  e) {
            Log.e("Disconnect:","Error disconnect.");
        }
    }

    @Override
    public void create(Person p) {
        try {
        dos.writeUTF("create");
        dos.flush();
        dos.writeUTF(gs.toJson(p));
        dos.flush();
        }
        catch (UnknownHostException  e) {
            Log.e("Create:","Error create.");
        }
        catch (IOException  e) {
            Log.e("Create:","Error create.");
        }


    }

    @Override
    public void update(Person p) {

    }

    @Override
    public void delete(Person p) {

    }

    @Override
    public LinkedList<Person> readAll() {
        return null;
    }

}
