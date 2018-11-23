package fr.husta.test.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MyPojoDto
        implements Serializable {

    private String name;
    private int age;
    private LocalDate dob;

}
