# WebService-Util

### An Android library to help you call webservice very easily, based on [KSoap 2](http://kobjects.org/ksoap2/index.html)

<img src="pic/1.png" width = "300" />
<img src="pic/2.png" width = "300" />

### Add the dependency

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
compile 'com.github.yunTerry:WebService-Util:2.0.0'
```

### Call the webservice

```java

HashMap<String, Object> hashMap = new HashMap<>();
hashMap.put("byProvinceName", "浙江");
// call WebService
new WsReqs("http://WebXml.com.cn/"  //name_space
        , "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx"  //url
        , "getSupportCity"  //method
        , hashMap     //Parameter set
        , String.class  //callback type
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

```
### Used in demo

[WeatherWebService](http://www.webxml.com.cn/WebServices/WeatherWebService.asmx)
