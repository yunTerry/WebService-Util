package com.terry.webservice;

/***
 * 名称：		WebServiceLisener
 * 作者：		Terry Tan
 * 创建时间：	    2016-1-20
 * 说明：
 **/
public interface WebServiceLisener {

    public void onSuccess(String method, Object result);

    public void onFail(Object msg);

}
