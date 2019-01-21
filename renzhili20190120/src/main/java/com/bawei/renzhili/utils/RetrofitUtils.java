package com.bawei.renzhili.utils;

import android.util.AndroidException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {
    private static RetrofitUtils mInsaner;
    private static final String BASE_URL="http://www.zhaoapi.cn/";
    private final BaseApis baseApis;

    public static RetrofitUtils getmInsaner() {
        if (mInsaner==null){
            synchronized (RetrofitUtils.class){
                mInsaner=new RetrofitUtils();
            }
        }
        return mInsaner;
    }

    private RetrofitUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);
    }
    //get
    public void get(String url,HttpListener httpListener){
        baseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpListener));
    }
    //post
    public void post(String url, Map<String,String> map,HttpListener httpListener){
        if (map==null){
            map=new HashMap<>();
        }
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpListener));
    }
    private Observer getObserver(final HttpListener httpListener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (httpListener!=null){
                    httpListener.failde(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try{
                    String data = responseBody.string();
                    if (httpListener!=null){
                        httpListener.success(data);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if (httpListener!=null){
                        httpListener.failde(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    public interface HttpListener{
        void success(String data);
        void failde(String error);
    }
}
