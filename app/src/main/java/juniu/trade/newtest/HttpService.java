package juniu.trade.newtest;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author lyd
 * @date 18/7/6
 * @desription
 */

public class HttpService {
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private HttpApis mHttpApis;
    private String mBaseUrl;
    private X509TrustManager mTrustManager;
    private SSLSocketFactory mSSLSocketFactory;

    private HttpService() {
//        mBaseUrl = "http://120.25.230.152:443";
        mBaseUrl = "https://www.myjuniu.com";
//        mBaseUrl = "https://www.baidu.com";
        initOkHttpClient();
        initRetrofit();
        mHttpApis = mRetrofit.create(HttpApis.class);
    }

    private static class singletonHolder {
        private static final HttpService instance = new HttpService();
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    public static HttpService getInstance() {
        return singletonHolder.instance;
    }

    private void initOkHttpClient() {
        //TODO SSL证书，现在不需要
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new HttpInterceptor());
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        //set retry on connection failure
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.retryOnConnectionFailure(false).build();
    }

    private void initRetrofit() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(mOkHttpClient);
        mRetrofit = retrofitBuilder.build();
    }

    private <T> Observable.Transformer<HttpResult<T>, T> rxScheduler() {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> observable) {
                return observable
                        .map(new HttpResultFunc<T>())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            return httpResult.getData();
        }
    }

    public Observable<JSONObject> login(String username, String password, String device) {
        return mHttpApis.login(username, password, device)
                .compose(this.<JSONObject>rxScheduler());
    }

    public Observable<JSONObject> storeProfile() {
        return mHttpApis.storeProfile("3581", "1533537764fb59a731ed07874703e9b54fee2a53c4mobile18318166399").compose(this.<JSONObject>rxScheduler());
    }

}
