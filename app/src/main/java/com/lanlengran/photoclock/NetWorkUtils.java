package com.lanlengran.photoclock;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.security.auth.callback.Callback;

public class NetWorkUtils {

    public static void get(final String url, final NetWorkCallBack callback) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String httpUrl = url;
                String resultData = "";
                URL url = null;
                try {
                    url = new URL(httpUrl);
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
                if (url != null) {
                    try {
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                        InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                        BufferedReader buffer = new BufferedReader(in);
                        String inputLine = null;
                        while (((inputLine = buffer.readLine()) != null)) {
                            resultData += inputLine + "\n";
                        }
                        in.close();
                        urlConn.disconnect();
                        if (callback != null) {
                            callback.onSuccuss(resultData);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (callback != null) {
                        callback.onError();
                    }
                }

            }
        }.start();
    }
    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {

            return paramString;
        }

        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {

        }

        return paramString;
    }

    interface NetWorkCallBack {
        void onSuccuss(String string);

        void onError();
    }
}
