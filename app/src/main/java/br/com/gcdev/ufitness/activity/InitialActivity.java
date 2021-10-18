package br.com.gcdev.ufitness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.gcdev.ufitness.R;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        clickOnBtnLogin();
        clickOnBtnCustomerRegistration();
    }

    private void clickOnBtnLogin() {
        Button btnGoToLogin = findViewById(R.id.button_go_to_login);
        btnGoToLogin.setOnClickListener(view -> openLoginActivity());
    }

    private void clickOnBtnCustomerRegistration() {
        Button btnGoToCustomerRegistration = findViewById(R.id.button_customer_registration);
        btnGoToCustomerRegistration.setOnClickListener(view -> openCustomerRegistrationActivity());
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void openCustomerRegistrationActivity() {
        startActivity(new Intent(this, CustomerRegistrationActivity.class));
    }
}