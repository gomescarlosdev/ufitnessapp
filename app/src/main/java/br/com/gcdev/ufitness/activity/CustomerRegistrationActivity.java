package br.com.gcdev.ufitness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.CustomerForm;
import br.com.gcdev.ufitness.data.form.dto.ClientDTO;
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
            if(isFormValid()){
                CustomerService customerService = new UfitnessRetrofit().getCustomerService();
                Call<ClientDTO> call = customerService.create(customerForm);
                try {
                    Response<ClientDTO> loginResponse = call.execute();
                    if(loginResponse.code() == 201){
                        openLoginActivity();
                    }else{
                        Toast.makeText(CustomerRegistrationActivity.this, "You are in", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void configThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private boolean isFormValid() {

        String name = editTextName.getText() != null ? editTextName.getText().toString() : "";
        String email = editTextEmail.getText() != null ? editTextEmail.getText().toString() : "";
        String password = editTextPassword.getText() != null ? editTextPassword.getText().toString() : "";
        String repeatPassword = editTextRepeatPassword.getText() != null ? editTextRepeatPassword.getText().toString() : "";

        return isNameValid(name) && isEmailValid(email) &&
                isPasswordValid(password) && arePasswordsMatch(password, repeatPassword);

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
        } else{
            editTextPassword.setError(null);
            return true;
        }
    }

    private boolean arePasswordsMatch(String password, String repeatPassword) {
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
