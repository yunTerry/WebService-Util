package com.terry.wsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.terry.webservice.WsIt;
import com.terry.webservice.WsReqs;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView wstv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wstv = (TextView) findViewById(R.id.wstv);
        findViewById(R.id.wsbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("theCityName", "杭州");
                new WsReqs("http://WebXml.com.cn/"
                        , "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx"
                        , "getWeatherbyCityName"
                        , hashMap
                        , String.class
                        , new WsIt() {
                    @Override
                    public void onSucces(String method, Object result) {
                        wstv.setText(result.toString());
                    }

                    @Override
                    public void onFailed(Object msg) {
                        wstv.setText(msg.toString());
                    }
                });
            }
        });
    }
}
