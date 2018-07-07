package juniu.trade.newtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long time = System.currentTimeMillis();
                Log.e("lyd", " httpService ");

//                HttpService httpService = HttpService.getInstance();
//                httpService.login("15627223506","a949500","device=00000000-11cc-3b47-da9b-098200000000").subscribe(new Subscriber<JSONObject>() {
////                httpService.login(null,null,null).subscribe(new Subscriber<JSONObject>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e("lyd"," onCompleted ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("lyd"," onError "+e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onNext(JSONObject jsonObject) {
//                        Log.e("lyd"," time "+(System.currentTimeMillis()-time));
//                        Log.e("lyd","  JSONObject  "+jsonObject.toJSONString());
//                    }
//                });
//                httpService.storeProfile().subscribe(new Subscriber<JSONObject>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e("lyd"," onCompleted ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("lyd"," onError "+e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onNext(JSONObject jsonObject) {
//                        Log.e("lyd"," time "+(System.currentTimeMillis()-time));
//                        Log.e("lyd","  JSONObject  "+jsonObject.toJSONString());
//                    }
//                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = HttpService.getInstance().getmOkHttpClient();//1.定义一个client
                        Request request = new Request.Builder().url("https://www.myjuniu.com/v1/store/profile/1254/token/1533522887eeeffbe98d76c1056dcd3258b9e64a3emobile18318166399").build();//2.定义一个request
                        Call call = okHttpClient.newCall(request);//3.使用client去请求
                        try {
                            String resule = call.execute().body().string();
                            Log.e("lyd"," 同步 "+resule);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

//                call.enqueue(new Callback() {//4.回调方法
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("lyd", " onFailure ");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String result = response.body().string();//5.获得网络数据
//                        Log.e("lyd", " onResponse " + result);
//                    }
//                });


//                StringRequest request = new StringRequest(Request.Method.GET, "https://www.myjuniu.com/v1/store/profile/1254/token/1533522887eeeffbe98d76c1056dcd3258b9e64a3emobile18318166399", new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("lyd", " onResponse " + response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                request.setRetryPolicy(new DefaultRetryPolicy(5*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                BaseApplication.getApp().getRequestQueue().add(request);
            }
        });
    }
}
