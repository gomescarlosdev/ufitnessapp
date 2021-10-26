package br.com.gcdev.ufitness.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.arePasswordsTheSame;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isDocumentValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isEmailValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isNameValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isPasswordValid;
import static br.com.gcdev.ufitness.validator.RegistrationValidator.isRegistrationNumberValid;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationValidatorTest {


    private TextInputEditText editText = mock(TextInputEditText.class);

    @Before
    public void setUp() {
        doNothing().when(editText).setError(any());
    }

    @Test
    public void name_is_not_valid() {
        assertFalse(isNameValid("", editText));
    }

    @Test
    public void name_is_valid() {
        assertTrue(isNameValid("Joao", editText));
    }

    @Test
    public void email_is_blank() {
        assertFalse(isEmailValid("", editText));
    }

    @Test
    public void email_is_not_valid_pattern() {
        assertFalse(isEmailValid("meu@email", editText));
    }

    @Test
    public void email_is_valid() {
        assertTrue(isEmailValid("meu@email.com", editText));
    }

    @Test
    public void password_is_blank() {
        assertFalse(isPasswordValid("", editText));
    }

    @Test
    public void password_is_too_short() {
        assertFalse(isPasswordValid("123", editText));
    }

    @Test
    public void password_is_ok() {
        assertTrue(isPasswordValid("123tesaaste", editText));
    }

    @Test
    public void repeat_password_is_blank() {
        assertFalse(arePasswordsTheSame("password11", "", editText));
    }

    @Test
    public void passwords_are_not_same() {
        assertFalse(arePasswordsTheSame("password11", "password22", editText));
    }

    @Test
    public void passwords_are_same() {
        assertTrue(arePasswordsTheSame("password11", "password11", editText));
    }

    @Test
    public void document_is_blank() {
        assertFalse(isDocumentValid("", editText));
    }

    @Test
    public void document_is_too_short() {
        assertFalse(isDocumentValid("1234", editText));
    }

    @Test
    public void document_is_ok() {
        assertTrue(isDocumentValid("121.121.121.11", editText));
    }

    @Test
    public void registration_number_is_blank() {
        assertFalse(isRegistrationNumberValid("", editText));
    }

    @Test
    public void registration_number_is_too_short() {
        assertFalse(isRegistrationNumberValid("1234", editText));
    }

    @Test
    public void registration_number_is_ok() {
        assertTrue(isRegistrationNumberValid("71261-G/SP", editText));
    }

}
