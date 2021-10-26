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
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isNameValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isEmailValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isPasswordValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.arePasswordsTheSame;

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

    private TextInputEditText editTextName, editTextEmail, editTextPassword, editTextRepeatPassword;

    private Button buttonSend;

    private String repeatPassword;

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
            fillForm();
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

        return isNameValid(customerForm.getName(), editTextName) &&
                isEmailValid(customerForm.getEmail(), editTextEmail) &&
                isPasswordValid(customerForm.getPassword(), editTextPassword) &&
                arePasswordsTheSame(customerForm.getPassword(), repeatPassword, editTextRepeatPassword);
    }

    private void fillForm(){
        customerForm.setName(editTextName.getText() != null ? editTextName.getText().toString() : "");
        customerForm.setEmail(editTextEmail.getText() != null ? editTextEmail.getText().toString() : "");
        customerForm.setPassword(editTextPassword.getText() != null ? editTextPassword.getText().toString() : "");
        repeatPassword = editTextRepeatPassword.getText() != null ? editTextRepeatPassword.getText().toString() : "";
    }
}
