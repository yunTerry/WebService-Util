package com.terry.webservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

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
 * 名称：		WsReqs
 * 作者：		Terry Tan
 * 创建时间：	2016-1-20
 * 说明：
 **/
public class WsReqs {

    public <T> WsReqs(final String namespace
            , final String serviceURL
            , final String methodName
            , final HashMap<String, Object> hashMap
            , final Class<T> resultType
            , final WsIt delegate) {

        new AsyncTask<Void, Void, Object>() {

            @Override
            protected Object doInBackground(Void... arg0) {
                // TODO Auto-generated method stub
                return call(namespace, serviceURL, methodName
                        , hashMap, delegate, resultType);
            }

            @Override
            protected void onPostExecute(Object result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if (result instanceof Exception)
                    delegate.onFailed(result);
                else
                    delegate.onSucces(methodName, result);
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

    private <T> Object call(String namespace, String serviceURL
            , String methodName, HashMap<String, Object> hmap,
                            WsIt delegate, Class<T> resultType) {

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
                delegate.onFailed(ex);
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
