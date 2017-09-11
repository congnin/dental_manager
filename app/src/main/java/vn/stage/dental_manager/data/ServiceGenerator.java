package vn.stage.dental_manager.data;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import vn.stage.dental_manager.data.model.AccessModel;
import vn.stage.dental_manager.data.model.ResponseModel;

/**
 * Created by congn on 9/11/2017.
 */

public class ServiceGenerator {
    private static final String API_BASE_URL = "http://153.126.150.57:5556";

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor);

    private static Retrofit getClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        builder.client(httpClient.build());
        return builder.build();
    }

    private static <S> S createServiceMain(Class<S> serviceClass) {
        String accessToken = UserPreferences.getAccessToken();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("X-Auth-Token", accessToken)
                    .addHeader("Content-Type", "application/json");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return getClient().create(serviceClass);
    }

    static <S> S createServiceLogin(
            Class<S> serviceClass, String username, String password) {

        final String authToken = Credentials.basic(username, password);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", authToken)
                    .header("Accept", "application/json")
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return getClient().create(serviceClass);
    }

    public static Observable<ResponseModel> login(String username, String password) {
        MyEndPoint loginService = createServiceLogin(MyEndPoint.class, username, password);
        return loginService.getTokenAdmin();
    }
}
