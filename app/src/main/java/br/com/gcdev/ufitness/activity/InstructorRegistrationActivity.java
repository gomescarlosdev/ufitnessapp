package br.com.gcdev.ufitness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.data.form.InstructorForm;
import br.com.gcdev.ufitness.retrofit.UfitnessRetrofit;
import br.com.gcdev.ufitness.service.InstructorService;
import retrofit2.Call;
import retrofit2.Response;

public class InstructorRegistrationActivity extends AppCompatActivity implements ConstantsActivities {

    private static final String REGISTRO = "Registro";

    private InstructorForm instructorForm = new InstructorForm();

    TextInputEditText editTextName, editTextEmail, editTextDocument, editTextRegistrationNumber, editTextPassword, editTextRepeatPassword;
    Button buttonSend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_registration);
        setTitle(REGISTRO);

        configThreadPolicy();

        getViewElements();
        onClickBtnSend();
    }

    private void getViewElements() {
        editTextName = findViewById(R.id.text_input_edit_instructor_name);
        editTextEmail = findViewById(R.id.text_input_edit_instructor_email);
        editTextDocument = findViewById(R.id.text_input_edit_instructor_document);
        editTextRegistrationNumber = findViewById(R.id.text_input_edit_instructor_registration_number);
        editTextPassword = findViewById(R.id.text_input_edit_instructor_password);
        editTextRepeatPassword = findViewById(R.id.text_input_edit_instructor_repeat_password);
        buttonSend = findViewById(R.id.activity_instructor_reg_btn_send);


        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTextDocument, smf);
        editTextDocument.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNNNN-L/LL");
        MaskTextWatcher mtw2 = new MaskTextWatcher(editTextRegistrationNumber, smf2);
        editTextRegistrationNumber.addTextChangedListener(mtw2);
    }

    private void onClickBtnSend() {
        buttonSend.setOnClickListener(v -> {
            if (isFormValid()) {
                createInstructor();
            }
        });
    }

    private void createInstructor() {
        try {
            InstructorService instructorService = new UfitnessRetrofit().getInstructorService();
            Call<?> call = instructorService.create(instructorForm);
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
        String document = editTextDocument.getText() != null ? editTextDocument.getText().toString() : "";
        String registrationNumber = editTextRegistrationNumber.getText() != null ? editTextRegistrationNumber.getText().toString() : "";
        String password = editTextPassword.getText() != null ? editTextPassword.getText().toString() : "";
        String repeatPassword = editTextRepeatPassword.getText() != null ? editTextRepeatPassword.getText().toString() : "";

        instructorForm.setName(name);
        instructorForm.setEmail(email);
        instructorForm.setPassword(password);
        instructorForm.setCpf(document.replace(".", "").replace("-", ""));
        instructorForm.setCref(registrationNumber);

        return isNameValid(name) &&
                isEmailValid(email) &&
                isDocumentValid(document) &&
                isRegistrationNumberValid(registrationNumber) &&
                isPasswordValid(password) &&
                arePasswordsTheSame(password, repeatPassword);
    }

    private boolean isNameValid(String name) {
        if (name.isEmpty()) {
            editTextName.setError(CANNOT_BE_EMPYT);
            return false;
        } else {
            editTextName.setError(null);
            return true;
        }
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
        } else if (password.length() < 8) {
            editTextPassword.setError(PASSWORD_T0O_SHORT);
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }

    private boolean arePasswordsTheSame(String password, String repeatPassword) {
        if (repeatPassword.isEmpty()) {
            editTextRepeatPassword.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (!password.equals(repeatPassword)) {
            editTextRepeatPassword.setError(PASSWORD_DONT_MATCH);
            return false;
        } else {
            editTextRepeatPassword.setError(null);
            return true;
        }
    }

    private boolean isDocumentValid(String document){
        if (document.isEmpty()) {
            editTextDocument.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (document.length() < 14) {
            editTextDocument.setError(DOCUMENT_TOO_SHORT);
            return false;
        } else {
            editTextDocument.setError(null);
            return true;
        }
    }

    private boolean isRegistrationNumberValid(String registrationNumber){
        if (registrationNumber.isEmpty()) {
            editTextRegistrationNumber.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (registrationNumber.length() < 10) {
            editTextRegistrationNumber.setError(CREF_TOO_SHORT);
            return false;
        } else {
            editTextRegistrationNumber.setError(null);
            return true;
        }
    }

}
