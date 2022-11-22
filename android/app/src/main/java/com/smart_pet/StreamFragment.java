package com.smart_pet;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class StreamFragment extends Fragment {
    Button sendBtn;
    WebView streamWebView;
    TextView read_textView;
    private Socket client;
    private DataInputStream dataInput;
    OutputStream sender;
    private final int portNum = 2091;
    private final String serverIP = "172.20.10.2";  // 라즈베리파이 ip
    private static int BUF_SIZE = 1024;
    private static String CONNECT_MSG = "connect";
    private static String STOP_MSG = "stop";

    private boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_stream, container, false);
        sendBtn = rootView.findViewById(R.id.sendBtn);
        streamWebView = rootView.findViewById(R.id.streamWebView);

        WebSettings webSettings = streamWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 화면이 계속 켜짐
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        streamWebView.setWebViewClient(new WebViewClient());
        //streamWebView.loadUrl("https://naver.com");  // test code
        streamWebView.loadUrl("http://172.20.10.2:8080/stream");  // 라즈베리 파이 ip 주소

        // tcp 통신 연결
        Connect connect = new Connect();
        connect.execute(CONNECT_MSG);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
            }
        });



        return rootView;
    }


    private class Connect extends AsyncTask< String , String,Void > {
        private String output_message;
        private String input_message;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                client = new Socket(serverIP, portNum);
                // dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());

                sender = client.getOutputStream();
                output_message = "test";
                byte[] data = output_message.getBytes();
                ByteBuffer b = ByteBuffer.allocate(4);
                b.order(ByteOrder.LITTLE_ENDIAN);
                b.putInt(data.length);
                sender.write(b.array(), 0 , 4);
                sender.write(data);

            } catch (UnknownHostException e) {
                String str = e.getMessage().toString();
                Log.d("discnt", str + " 1");
            } catch (IOException e) {
                String str = e.getMessage().toString();
                Log.d("discnt", str + " 2");
            }

            while (true){
                try {
                    byte[] buf = new byte[BUF_SIZE];
                    int read_Byte  = dataInput.read(buf);
                    input_message = new String(buf, 0, read_Byte);
                    Log.d("TCP", "받은 메시지:" + input_message);

                    Log.d("flagcc", String.valueOf(flag));

                    if(flag){
                        byte[] data = output_message.getBytes();
                        ByteBuffer b = ByteBuffer.allocate(4);
                        b.order(ByteOrder.LITTLE_ENDIAN);
                        b.putInt(data.length);
                        sender.write(b.array(), 0 , 4);
                        sender.write(data);

                        flag=false;
                    }

                    if (!input_message.equals(STOP_MSG)){
                        publishProgress(input_message);
                    }
                    else{
                        break;
                    }



                    Thread.sleep(1);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... params){
            // read_textView.setText(""); // Clear the chat box
            //  read_textView.append("받은 메세지: " + params[0]);
        }
    }
}