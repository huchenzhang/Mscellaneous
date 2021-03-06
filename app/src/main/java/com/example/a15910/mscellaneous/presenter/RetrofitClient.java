package com.example.a15910.mscellaneous.presenter;

import android.util.Log;

import com.example.a15910.mscellaneous.model.ApiService;
import com.example.a15910.mscellaneous.HttpUrl;
import com.example.a15910.mscellaneous.constant.Constants;
import com.example.a15910.mscellaneous.bean.AccountBean;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * mvp中的P，view和数据之间的桥梁
 * Created by hu on 2018/4/19.
 */

public class RetrofitClient {
    private static RetrofitClient retrofitClient;

    private ApiService apiService;

    public static RetrofitClient getInstance() {
        return retrofitClient != null ? retrofitClient : (retrofitClient = new RetrofitClient());
    }


    private RetrofitClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RepostInterceptor())
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        //初始化Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(HttpUrl.BASE_URL)
                //增加返回值为String的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * 同下，指定运行线程
     *  .subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
     *  .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更新UI
     * @return Observable.compose(ObservableTransformer)
     */
    private ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {

            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /***
     * 自定义OkHttp Interceptor实现日志输出
     * 可以保存和添加Cookie
     */
    static class RepostInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.e("网络", "你访问的地址:" + request.url());
            Response proceed = chain.proceed(request);
            Log.e("网络", "你访问的地址返回:" + request.url() + "返回的信息: " + proceed.peekBody(Integer.MAX_VALUE).string());
            return proceed;
        }
    }

    /**
     * 登陆，检查手机号和密码是否正确
     * @param phoneNumber 手机号
     * @param password 密码的MD5值
     * @return ObservableOnSubscribe<List<AccountBean>> 查询出来的账号集合
     */
    public ObservableOnSubscribe<List<AccountBean>> login(String phoneNumber,String password) {
        //也可以用下面的方法指定线程
//        return apiService.getPhoneLocationGetObservable(url, key, phoneNumber).compose(schedulersTransformer());
        return null;
    }
}
