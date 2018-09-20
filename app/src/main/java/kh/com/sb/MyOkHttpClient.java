package kh.com.sb;

import okhttp3.OkHttpClient;

public class MyOkHttpClient extends OkHttpClient {

    private static OkHttpClient myOkhttpClient;

    public static OkHttpClient getInstance() {
        if (myOkhttpClient == null) {
            myOkhttpClient = new OkHttpClient();
        }
        return myOkhttpClient;
    }
}