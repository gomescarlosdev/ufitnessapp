package br.com.gcdev.ufitness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.CustomerForm;
import br.com.gcdev.ufitness.data.form.dto.ClientDTO;
import br.com.gcdev.ufitness.retrofit.UfitnessRetrofit;
import br.com.gcdev.ufitness.service.CustomerService;
import retrofit2.Call;
import retrofit2.Response;

public class CustomerRegistrationActivity extends AppCompatActivity {

    private static final String REGISTRO = "Registro";
    private CustomerForm customerForm = new CustomerForm();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        setTitle(REGISTRO);
        Button buttonSend = findViewById(R.id.activity_customer_reg_btn_send);
        buttonSend.setOnClickListener(v -> {

            fillCustomerForm();
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

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

    private void fillCustomerForm() {

        TextInputEditText editTextName = findViewById(R.id.text_input_edit_customer_name);
        TextInputEditText editTextEmail = findViewById(R.id.text_input_edit_customer_email);
        TextInputEditText editTextPassword = findViewById(R.id.text_input_edit_customer_password);
        TextInputEditText editTextRepeatPassword = findViewById(R.id.text_input_edit_customer_repeat_password);

        if(editTextName.getText() != null && editTextEmail.getText() != null &&
                editTextPassword.getText() != null){
            customerForm.setName(editTextName.getText().toString());
            customerForm.setEmail(editTextEmail.getText().toString());
            customerForm.setPassword(editTextPassword.getText().toString());
        }
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
