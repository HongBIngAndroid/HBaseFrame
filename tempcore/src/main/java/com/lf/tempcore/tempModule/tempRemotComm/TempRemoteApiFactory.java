package com.lf.tempcore.tempModule.tempRemotComm;

import com.lf.tempcore.tempConfig.TempURIConfig;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 构建远程接口的factory
 * Created by Zhijun.Pu on 2015/12/22.
 */
public class TempRemoteApiFactory {
//    private static final String BASE_URL = "http://192.168.1.2:8999/ibds/app/";
//    private static final String BASE_URL = "http://192.168.0.144:8080/";
//    private static final String BASE_URL = "http://192.168.0.7:8090/";
//    private static final String BASE_URL = ;//张馨予
//    private static final String BASE_URL = "http://192.168.0.117:8080/";

    private static Retrofit retrofit;
    private static OkHttpClient client;
    private static TempUserInfoAccessable userInfoAccessable = TempUserInfoAccessableImpl.getInstance();

    static {
        client = new OkHttpClient();

        // 用户验证
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder().addHeader("User-Agent", buildUserIdentify()).build();
                Request newRequest = chain.request().newBuilder().build();
//                Request request = chain.request();
//                long t1 = System.nanoTime();
//                Debug.info(String.format("Sending request %s on %s%n%s",
//                        request.url(), chain.connection(), request.headers()));
//
//                Response response = chain.proceed(request);
//
//                long t2 = System.nanoTime();
//                Debug.info(String.format("Received response for %s in %.1fms%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return chain.proceed(newRequest);
            }
        };
        client.interceptors().add(interceptor);

        retrofit = new Retrofit.Builder().baseUrl(TempURIConfig.BASE_SERVICE_URL).
//        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
        client(client).
                        addConverterFactory(GsonConverterFactory.create()).
//                addConverterFactory(StringConverterFactory.create()).
        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        build();

//        requestBody = new MultipartBuilder().
//                type(MultipartBuilder.FORM).
//                addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"mycustomfile.png\""),
//                    RequestBody.create(MEDIA_TYPE_PNG, file))
//            .build();
    }

    private TempRemoteApiFactory() {
    }

    /**
     * 构造用户认证的头信息
     * @return
     */
    private static String buildUserIdentify() {
        final String localUserIdentify = userInfoAccessable.getLocalUserIdentity();
        final Long userId = userInfoAccessable.getUserId();
        final String username = userInfoAccessable.getUsername();
        final String encryptPwd = userInfoAccessable.getEncryptPassword();
//        if (StringUtils.isNotBlank(localUserIdentify)) {
//            return localUserIdentify;
//        } else {
//        Debug.info("构造用户认证的头信息="+username + "|" + userId + "|" + encryptPwd + "|" + localUserIdentify);
        return username + "|" + userId + "|" + encryptPwd + "|" + localUserIdentify;
//        }

    }

//    public static void initUserInfoAccessable(UserInfoAccessableBuilder bulider){
//        bulider.build(userInfoAccessable);
//    }
//    public interface UserInfoAccessableBuilder{
//        void build(UserInfoAccessableImpl userInfoAccessable);
//    }
    /**
     * 构造远程接口对象
     *
     * @param clazz 接口的interface class
     * @param <API>
     * @return
     */
    public static <API> API createRemoteApi(Class<API> clazz) {
        return retrofit.create(clazz);
    }
    public static <T> void executeMethod(T API){
    }
    public static<T> void executeMethod(Observable<T> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(T o) {
//                        Toast.makeText(context.this, o., Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static <T> void executeMethod(Observable<T> observable, final OnCallBack<T> callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        if (callback != null) {
                            callback.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onSucceed(o);
                        }
                    }
                });
    }

    public interface OnCallBack<T> {
        void onSucceed(T data);

        void onCompleted();

        void onError(Throwable e);


    }
    interface OnProgressCallBack<T> extends OnCallBack<T>{
        void onLoading(long total, long current, boolean isUploading);
    }
}
