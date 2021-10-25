package br.com.gcdev.ufitness.data.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorForm {

    private String email;
    private String name;
    private String password;
    private String cpf;
    private String cref;

}
