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

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.LoginForm;
import br.com.gcdev.ufitness.retrofit.UfitnessRetrofit;
import br.com.gcdev.ufitness.service.LoginService;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    private static final String CONECTAR = "Conectar";
    private static final String CANNOT_BE_EMPYT = "Campo não pode estar vazio";

    private LoginForm loginForm = new LoginForm();

    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonSend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(CONECTAR);
        getViewElements();
        onClickBtnConnect();
    }

    private void getViewElements(){
        editTextEmail = findViewById(R.id.text_input_edit_login_email);
        editTextPassword = findViewById(R.id.text_input_edit_login_password);
        buttonSend = findViewById(R.id.activity_login_btn_connectar);
    }

    private void onClickBtnConnect() {
        buttonSend.setOnClickListener(v -> {
            if(isFormValid()){
                configThreadPolicy();
//                doLogin();
            }
        });
    }

    private void configThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void openHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    private boolean isFormValid() {
       String email = editTextEmail.getText() != null ? editTextEmail.getText().toString() : "";
       String password = editTextPassword.getText() != null ? editTextPassword.getText().toString() : "";

       if(email.isEmpty()){
           editTextEmail.setError(CANNOT_BE_EMPYT);
           return false;
       }else{
           editTextEmail.setError(null);

       }
       if(password.isEmpty()){
           editTextPassword.setError(CANNOT_BE_EMPYT);
           return false;
       }else{
           editTextPassword.setError(null);
       }
       return true;
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

}
