package fr.husta.test.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class MyPojoDto implements Serializable {

    private String name;
    private int age;
    private LocalDate dob;

    public MyPojoDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
