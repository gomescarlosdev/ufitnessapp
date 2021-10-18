package br.com.gcdev.ufitness.retrofit;

import br.com.gcdev.ufitness.service.CustomerService;
import br.com.gcdev.ufitness.service.LoginService;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class UfitnessRetrofit {

    private final LoginService loginService;
    private final CustomerService customerService;

    public UfitnessRetrofit(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://ufitness-proj-api.herokuapp.com/api/v1/ufitness/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginService = retrofit.create(LoginService.class);
        customerService= retrofit.create(CustomerService.class);
    }

}
