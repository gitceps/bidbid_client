package com.aze51.bidbid_client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    NetworkService networkService;
    private static final String IP_PATTERN =
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Connecting();
        initNetworkService();
        connectServer();

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        }, 700);
    }

    public void connectServer() {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<User> loginTest = networkService.getSession();
        loginTest.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                connectSuccess(response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                networkError();
            }
        });
    }

    public void connectSuccess(int statusCode) {
        Intent intent;
        if(statusCode == 200){
            Log.d("MyTag", "Has session");
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(statusCode == 401){
            Log.d("MyTag", "Has no session ");
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }
        else{
            return;
        }

        startActivity(intent);
        finish();
    }

    private void initNetworkService() {
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();

    }

    protected void Connecting() {
        String ip = "52.78.66.175";
        if (TextUtils.isEmpty(ip) || ip.matches(IP_PATTERN) != true) {
            Toast.makeText(getApplicationContext(), "Invaild IP Pattern", Toast.LENGTH_LONG).show();
            return;
        }
        String portString = "3000";
        if (TextUtils.isEmpty(portString) || TextUtils.isDigitsOnly(portString) != true) {
            Toast.makeText(getApplicationContext(), "Invaild port value",
                    Toast.LENGTH_LONG).show();
            return;
        }
        int port = Integer.parseInt(portString);
        if (0 > port || port > 65535) {
            Toast.makeText(getApplicationContext(), "Invalid port value",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //ApplicationController application = new ApplicationController();
        ApplicationController application = ApplicationController.getInstance();
        application.buildNetworkService(ip, port);
    }

    public void networkError() {
        Toast.makeText(getApplicationContext(), "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
    }
}