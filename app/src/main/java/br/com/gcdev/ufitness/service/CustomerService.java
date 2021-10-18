package br.com.gcdev.ufitness.service;


import br.com.gcdev.ufitness.data.form.CustomerForm;
import br.com.gcdev.ufitness.data.form.dto.ClientDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerService {

    @POST("client")
    Call<ClientDTO> create(@Body CustomerForm customerForm);

}
