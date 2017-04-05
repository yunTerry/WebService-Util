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
compile 'com.github.yunTerry:WebService-Util:3.0.0'
```

### Call the webservice

```java

WebService.init("url", "namespace", backClass);

HashMap<String, Object> hashMap = new HashMap<>();
hashMap.put("byProvinceName", "浙江");

WebService.request("getSupportCity", hashMap, new WebServiceLisener() {
    @Override
    public void onSuccess(String method, Object result) {

    }

    @Override
    public void onFail(Object msg) {

    }
});

```
### Used in demo

[WeatherWebService](http://www.webxml.com.cn/WebServices/WeatherWebService.asmx)
