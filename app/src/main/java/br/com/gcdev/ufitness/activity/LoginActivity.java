package br.com.gcdev.ufitness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.LoginForm;
import br.com.gcdev.ufitness.retrofit.UfitnessRetrofit;
import br.com.gcdev.ufitness.service.LoginService;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    private static final String CONECTAR = "Conectar";
    private LoginForm loginForm = new LoginForm();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(CONECTAR);
        configBtnConnect();
    }

    private void fillLoginForm() {
        TextInputEditText editTextEmail = findViewById(R.id.text_input_edit_login_email);
        TextInputEditText editTextPassword = findViewById(R.id.text_input_edit_login_password);
        if(editTextEmail.getText() != null && editTextPassword.getText() != null){
            loginForm.setEmail(editTextEmail.getText().toString());
            loginForm.setPassword(editTextPassword.getText().toString());
        }
    }

    private void configBtnConnect() {
        Button buttonSend = findViewById(R.id.activity_login_btn_connectar);
        buttonSend.setOnClickListener(v -> {
                fillLoginForm();
                configThreadPolicy();
                doLogin();
        });
    }

    private void doLogin() {
        LoginService loginService = new UfitnessRetrofit().getLoginService();
        Call<Map<String, String>> call = loginService.doLogin(loginForm);
        try {
            if(call.execute().code() == 200){
                openHomeActivity();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void openHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }

}
