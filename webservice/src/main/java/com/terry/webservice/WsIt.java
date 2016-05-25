package com.terry.webservice;

/***
 * 名称：		WsIt
 * 作者：		Terry Tan
 * 创建时间：	2016-1-20
 * 说明：
 **/
public interface WsIt {

    public void onSucces(String method, Object result);

    public void onFailed(Object msg);

}
