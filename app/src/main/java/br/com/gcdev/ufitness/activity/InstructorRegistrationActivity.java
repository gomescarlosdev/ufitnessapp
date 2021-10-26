package br.com.gcdev.ufitness.activity;

import static br.com.gcdev.ufitness.validator.RegistrationValidator.arePasswordsTheSame;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isDocumentValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isEmailValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isNameValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isPasswordValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isRegistrationNumberValid;

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

    private TextInputEditText editTextName, editTextEmail, editTextDocument, editTextRegistrationNumber, editTextPassword, editTextRepeatPassword;

    private Button buttonSend;

    private String repeatPassword;


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
            fillForm();
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
        return isNameValid(instructorForm.getName(), editTextName) &&
                isEmailValid(instructorForm.getEmail(), editTextEmail) &&
                isDocumentValid(instructorForm.getCpf(), editTextDocument) &&
                isRegistrationNumberValid(instructorForm.getCref(), editTextRegistrationNumber) &&
                isPasswordValid(instructorForm.getPassword(), editTextPassword) &&
                arePasswordsTheSame(instructorForm.getPassword(), repeatPassword, editTextRepeatPassword);
    }

    private void fillForm(){
        instructorForm.setName(editTextName.getText() != null ? editTextName.getText().toString() : "");
        instructorForm.setEmail(editTextEmail.getText() != null ? editTextEmail.getText().toString() : "");
        instructorForm.setCpf(editTextDocument.getText() != null ? editTextDocument.getText()
                .toString().replace(".", "").replace("-", "") : "");
        instructorForm.setCref(editTextRegistrationNumber.getText() != null ? editTextRegistrationNumber.getText().toString() : "");
        instructorForm.setPassword(editTextPassword.getText() != null ? editTextPassword.getText().toString() : "");
        repeatPassword = editTextRepeatPassword.getText() != null ? editTextRepeatPassword.getText().toString() : "";
    }

}
