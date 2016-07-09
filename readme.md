#WebService-Util
##An Android library to call webservice very easily
<img src="pic/1.png" width = "300" />
<img src="pic/2.png" width = "300" />
###Add the dependency
##### step 1
```gradle
allprojects {
	repositories {
		...
		maven { url "https://www.jitpack.io" }
	}
}
```
##### step 2
```gradle
compile 'com.github.yunTerry:ws-util:2.0.0'
```

###Call the webservice
```java
wsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("byProvinceName", "浙江");
                new WsReqs("http://WebXml.com.cn/"  //name_space
                        , "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx" //url
                        , "getSupportCity"  //method
                        , hashMap
                        , String.class  //return type
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
```