package com.android.libs.ext.http;

import java.net.HttpURLConnection;

/**
 * Created by E on 2018/4/2.
 */
public class RequestResult {

    private int responseCode = 0;
    private HttpURLConnection httpURLConnection = null;

    public RequestResult() {
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }
}
