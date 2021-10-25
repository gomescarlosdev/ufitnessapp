package br.com.gcdev.ufitness.service;


import java.util.Map;

import br.com.gcdev.ufitness.data.form.InstructorForm;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InstructorService {

    @POST("instructor")
    Call<Map<String, String>> create(@Body InstructorForm instructorForm);

}
