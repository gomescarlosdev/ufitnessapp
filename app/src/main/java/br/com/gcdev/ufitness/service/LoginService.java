package br.com.gcdev.ufitness.service;

import java.util.Map;

import br.com.gcdev.ufitness.data.form.LoginForm;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<Map<String, String>> doLogin(@Body LoginForm loginForm);

}
