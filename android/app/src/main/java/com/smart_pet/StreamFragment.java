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
import java.net.Socket;
import java.net.UnknownHostException;


public class StreamFragment extends Fragment {
    Button sendBtn;
    WebView streamWebView;
    TextView read_textView;
    private Socket client;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;
    private final int portNum = 7555;
    private final String serverIP = "172.20.10.2";  // 라즈베리파이 ip
    private static int BUF_SIZE = 1024;
    private static String CONNECT_MSG = "connect";
    private static String STOP_MSG = "stop";

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

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connect connect = new Connect();
                connect.execute(CONNECT_MSG);
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
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());
                output_message = strings[0];
                dataOutput.writeUTF(output_message);

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
                    if (!input_message.equals(STOP_MSG)){
                        publishProgress(input_message);
                    }
                    else{
                        break;
                    }
                    Thread.sleep(2);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... params){
            read_textView.setText(""); // Clear the chat box
            read_textView.append("받은 메세지: " + params[0]);
        }
    }
}