package com.terry.wsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.terry.webservice.WebService;
import com.terry.webservice.WebServiceLisener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView wstv;
    String URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
    String NAME = "http://WebXml.com.cn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wstv = (TextView) findViewById(R.id.wstv);
        WebService.init(URL, NAME);
        findViewById(R.id.wsbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("byProvinceName", "浙江");
                WebService.request("getSupportCity", hashMap, new WebServiceLisener() {
                    @Override
                    public void onSuccess(String method, Object result) {
                        wstv.setText(result.toString());
                    }

                    @Override
                    public void onFail(Object msg) {
                        wstv.setText(msg.toString());
                    }
                });
            }
        });
    }
}
