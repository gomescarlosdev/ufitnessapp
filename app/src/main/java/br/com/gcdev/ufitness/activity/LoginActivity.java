package br.com.gcdev.ufitness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.LoginForm;
import br.com.gcdev.ufitness.retrofit.UfitnessRetrofit;
import br.com.gcdev.ufitness.service.LoginService;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ConstantsActivities {

    private static final String CONECTAR = "Conectar";

    private LoginForm loginForm = new LoginForm();

    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonSend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(CONECTAR);

        configThreadPolicy();

        getViewElements();
        onClickBtnConnect();
    }

    private void getViewElements() {
        editTextEmail = findViewById(R.id.text_input_edit_login_email);
        editTextPassword = findViewById(R.id.text_input_edit_login_password);
        buttonSend = findViewById(R.id.activity_login_btn_connectar);
    }

    private void onClickBtnConnect() {
        buttonSend.setOnClickListener(v -> {
            if (isFormValid()) {
                doLogin();
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

        loginForm.setEmail(email);
        loginForm.setPassword(password);

        return isEmailValid(email) && isPasswordValid(password);
    }

    private boolean isEmailValid(String email) {
        if (email.isEmpty()) {
            editTextEmail.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (!email.matches(EMAIL_PATTERN)) {
            editTextEmail.setError(EMAIL_INVALID);
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.isEmpty()) {
            editTextPassword.setError(CANNOT_BE_EMPYT);
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }

    private void doLogin() {
        try {
            LoginService loginService = new UfitnessRetrofit().getLoginService();
            Call<Map<String, String>> call = loginService.doLogin(loginForm);
            Response<?> response = call.execute();
            if (response.code() == 200) {
                openHomeActivity();
            } else if (response.code() == 400) {
                if (response.errorBody() != null && !response.errorBody().toString().isEmpty()) {
                    Toast.makeText(this, new JSONObject(response.errorBody().string()).getString("error"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
