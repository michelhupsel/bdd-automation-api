package bdd.automation.api.dominio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @JsonAlias("first_name")
    private String name;
    private String job;
    private String email;
    @JsonAlias("last_name")
    private String lastName;

}
