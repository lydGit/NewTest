package juniu.trade.newtest;

import android.app.Application;

import com.android.volley.RequestQueue;

/**
 * @author lyd
 * @date 18/7/7
 * @desription
 */

public class BaseApplication extends Application{

    private static RequestQueue requestQueue;

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
        requestQueue = RequestQueueUtil.getRequestQueue(this);
    }

    public static BaseApplication getApp(){
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
