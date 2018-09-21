package kh.com.sb;

import okhttp3.OkHttpClient;

public class MyOkHttpClient extends OkHttpClient {

    private static OkHttpClient myOKhttpClient;

    public MyOkHttpClient() {
        super();
    }

    public static OkHttpClient getInstance() {
        if (myOKhttpClient == null) {
            myOKhttpClient = new MyOkHttpClient();
        }
        return myOKhttpClient;
    }
}