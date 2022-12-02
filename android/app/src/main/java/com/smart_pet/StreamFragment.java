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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class StreamFragment extends Fragment {
    Button sendBtn;
    WebView streamWebView;
    public static boolean flag = false;

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
                flag = true;
            }
        });

        new ServerThread().start();



        return rootView;
    }

    class ServerThread extends Thread {
        @Override
        public void run() {
            int port = 5003;
            Socket socket = null;
            try {
                ServerSocket server = new ServerSocket(port);
                Log.d("ServerThread", "Server Started.");

                if((socket =server.accept()) != null) {
                    InetAddress clientHost = socket.getLocalAddress();
                    int clientPort = socket.getPort();
                    Log.d("ServerThread", "클라이언트 연결됨. 호스트 : " + clientHost + ", 포트 : " + clientPort);

                    while (true) {
                        if(flag) {
                            ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                            outstream.writeObject("1");
                            outstream.flush();
                            Log.d("ServerThread", "output sent.");
                            flag = false;
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

}