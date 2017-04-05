package com.terry.webservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/***
 * 名称：		WebService
 * 作者：		Terry Tan
 * 创建时间：	    2016-1-20
 * 说明：
 **/
public class WebService {

    private static String url = "";
    private static String namespace = "";
    private static Class backtype = String.class;

    public static void init(String ws_url, String ws_namespace) {
        url = ws_url;
        namespace = ws_namespace;
    }

    public static void init(String ws_url, String ws_namespace, Class ws_backtype) {
        url = ws_url;
        namespace = ws_namespace;
        backtype = ws_backtype;
    }

    public static void request(final String method, final HashMap<String, Object> hashMap, final WebServiceLisener lisener) {

        new AsyncTask<Void, Void, Object>() {

            @Override
            protected Object doInBackground(Void... arg0) {
                // TODO Auto-generated method stub
                return call(namespace, url, method
                        , hashMap, lisener, backtype);
            }

            @Override
            protected void onPostExecute(Object result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if (result instanceof Exception)
                    lisener.onFail(result);
                else
                    lisener.onSuccess(method, result);
            }

        }.execute();
    }

    private static <T> Object unmarshalSoapObjeResponse(SoapObject soapObject,
                                                        Class<T> resultType) {
        return soapObject;
    }

    @SuppressLint("SimpleDateFormat")
    private static <T> Object unmarshalSoapPimitiveResponse(Object retObj,
                                                            Class<T> resultType) {
        Object object = null;
        SoapPrimitive j = (SoapPrimitive) retObj;
        try {
            if (resultType == int.class) {
                object = Integer.valueOf(j.toString());
            } else if (resultType == long.class) {
                object = Long.parseLong(j.toString());
            } else if (resultType == float.class) {
                object = Float.parseFloat(j.toString());
            } else if (resultType == double.class) {
                object = Double.parseDouble(j.toString());
            } else if (resultType == boolean.class) {
                object = Boolean.parseBoolean(j.toString());
            } else if (resultType == String.class) {
                object = j.toString();
            } else if (resultType == Date.class) {
                object = (new SimpleDateFormat("yyyy-MM-dd")).parse(j
                        .toString());
            } else if (resultType == byte[].class) {
                object = j.toString();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return object;
    }

    private static <T> Object call(String namespace, String serviceURL
            , String methodName, HashMap<String, Object> hmap,
                                   WebServiceLisener delegate, Class<T> resultType) {

        Object object = null;

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER12);

        SoapObject requestSoapObject = new SoapObject(namespace, methodName);

        if (hmap != null) {
            Set<String> set = hmap.keySet();
            for (String key : set) {
                requestSoapObject.addProperty(key, hmap.get(key));
            }
        }

        envelope.implicitTypes = true;
        envelope.dotNet = true;
        envelope.bodyOut = requestSoapObject;

        HttpTransportSE httpTransportSE = new HttpTransportSE(serviceURL);
        httpTransportSE.debug = true;
        try {
            httpTransportSE.call(namespace + methodName, envelope);
            Object retObj = envelope.getResponse();

            //return retObj;

            if (retObj instanceof SoapFault) {
                SoapFault fault = (SoapFault) retObj;
                Exception ex = new Exception(fault.faultstring);
                delegate.onFail(ex);
            } else if (retObj instanceof SoapPrimitive) {
                SoapPrimitive soapPrimitive = (SoapPrimitive) retObj;
                object = unmarshalSoapPimitiveResponse(retObj, resultType);
                return object;
            } else if (retObj instanceof Vector) {
                object = (Object) retObj;
                return object;
            } else {
                SoapObject soapObject = (SoapObject) retObj;

                object = unmarshalSoapObjeResponse(soapObject, resultType);
                return object;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return e;
        }
        return null;
    }
}
