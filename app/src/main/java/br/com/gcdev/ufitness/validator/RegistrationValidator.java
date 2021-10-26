package br.com.gcdev.ufitness.validator;

import com.google.android.material.textfield.TextInputEditText;

public class RegistrationValidator {

    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String CANNOT_BE_EMPYT = "Campo não pode estar vazio";
    private static final String PASSWORD_T0O_SHORT = "A senha deve ter no mínimo 8 caracteres";
    private static final String EMAIL_INVALID = "Email inválido";
    private static final String PASSWORD_DONT_MATCH = "As senhas não podem ser diferentes";
    private static final String DOCUMENT_TOO_SHORT = "O CPF deve ter no mínimo 11 digitos";
    private static final String REGISTRATION_NUMBER_TOO_SHORT = "O CREF deve ter no mínimo 10 digitos";

    public static boolean isNameValid(String name, TextInputEditText editText) {
        if(name.isEmpty()){
            editText.setError(CANNOT_BE_EMPYT);
            return false;
        }else{
            editText.setError(null);
            return true;
        }
    }

    public static boolean isEmailValid(String email, TextInputEditText editText) {
        if(email.isEmpty()){
            editText.setError(CANNOT_BE_EMPYT);
            return false;
        }else if (!email.matches(EMAIL_PATTERN)){
            editText.setError(EMAIL_INVALID);
            return false;
        }else{
            editText.setError(null);
            return true;
        }
    }

    public static boolean isPasswordValid(String password, TextInputEditText editText) {
        if(password.isEmpty()){
            editText.setError(CANNOT_BE_EMPYT);
            return false;
        }else if (password.length() < 8){
            editText.setError(PASSWORD_T0O_SHORT);
            return false;
        }else{
            editText.setError(null);
            return true;
        }
    }

    public static boolean arePasswordsTheSame(String password, String repeatPassword, TextInputEditText editText) {
        if(repeatPassword.isEmpty()){
            editText.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (!password.equals(repeatPassword)){
            editText.setError(PASSWORD_DONT_MATCH);
            return false;
        } else{
            editText.setError(null);
            return true;
        }
    }

    public static boolean isDocumentValid(String document, TextInputEditText editText){
        if (document.isEmpty()) {
            editText.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (document.length() < 11) {
            editText.setError(DOCUMENT_TOO_SHORT);
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean isRegistrationNumberValid(String registrationNumber, TextInputEditText editText){
        if (registrationNumber.isEmpty()) {
            editText.setError(CANNOT_BE_EMPYT);
            return false;
        } else if (registrationNumber.length() < 10) {
            editText.setError(REGISTRATION_NUMBER_TOO_SHORT);
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }
}
