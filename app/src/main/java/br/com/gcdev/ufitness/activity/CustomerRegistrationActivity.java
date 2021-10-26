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

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.CustomerForm;
import br.com.gcdev.ufitness.retrofit.UfitnessRetrofit;
import br.com.gcdev.ufitness.service.CustomerService;
import retrofit2.Call;
import retrofit2.Response;

public class CustomerRegistrationActivity extends AppCompatActivity implements ConstantsActivities{

    private static final String REGISTRO = "Registro";

    private CustomerForm customerForm = new CustomerForm();

    TextInputEditText editTextName, editTextEmail, editTextPassword, editTextRepeatPassword;
    Button buttonSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        setTitle(REGISTRO);

        configThreadPolicy();

        getViewElements();
        onClickBtnSend();
    }

    private void getViewElements(){
        editTextName = findViewById(R.id.text_input_edit_customer_name);
        editTextEmail = findViewById(R.id.text_input_edit_customer_email);
        editTextPassword = findViewById(R.id.text_input_edit_customer_password);
        editTextRepeatPassword = findViewById(R.id.text_input_edit_customer_repeat_password);
        buttonSend = findViewById(R.id.activity_customer_reg_btn_send);
    }

    private void onClickBtnSend() {
        buttonSend.setOnClickListener(v -> {
            if (isFormValid()) {
                createCustomer();
            }
        });
    }

    private void createCustomer() {
        try {
            CustomerService customerService = new UfitnessRetrofit().getCustomerService();
            Call<?> call = customerService.create(customerForm);
            Response<?> response = call.execute();
            if (response.code() == 201) {
                openLoginActivity();
            } else if (response.code() == 400) {
                if (response.errorBody() != null) {
                    Toast.makeText(this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Falha ao tentar realizar o cadastro", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Falha ao tentar realizar o cadastro", Toast.LENGTH_LONG).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void configThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private boolean isFormValid() {

        String name = editTextName.getText() != null ? editTextName.getText().toString() : "";
        String email = editTextEmail.getText() != null ? editTextEmail.getText().toString() : "";
        String password = editTextPassword.getText() != null ? editTextPassword.getText().toString() : "";
        String repeatPassword = editTextRepeatPassword.getText() != null ? editTextRepeatPassword.getText().toString() : "";

        customerForm.setName(name);
        customerForm.setEmail(email);
        customerForm.setPassword(password);

        return isNameValid(name) && isEmailValid(email) &&
                isPasswordValid(password) && arePasswordsTheSame(password, repeatPassword);

    }

    private boolean isNameValid(String name) {
        if(name.isEmpty()){
            editTextName.setError(CANNOT_BE_EMPYT);
            return false;
        }else{
            editTextName.setError(null);
            return true;
        }
    }

    private boolean isEmailValid(String email) {
        if(email.isEmpty()){
            editTextEmail.setError(CANNOT_BE_EMPYT);
            return false;
        }else if (!email.matches(EMAIL_PATTERN)){
            editTextEmail.setError(EMAIL_INVALID);
            return false;
        }else{
            editTextEmail.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid(String password) {
        if(password.isEmpty()){
            editTextPassword.setError(CANNOT_BE_EMPYT);
            return false;
        }else if (password.length() < 8){
            editTextPassword.setError(PASSWORD_T0O_SHORT);
            return false;
        }else{
            editTextPassword.setError(null);
            return true;
        }
    }

    private boolean arePasswordsTheSame(String password, String repeatPassword) {
        if(repeatPassword.isEmpty()){
            editTextRepeatPassword.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (!password.equals(repeatPassword)){
            editTextRepeatPassword.setError(PASSWORD_DONT_MATCH);
            return false;
        } else{
            editTextRepeatPassword.setError(null);
            return true;
        }
    }

}
