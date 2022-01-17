package com.example.demo.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name="t_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Role {USER, ADMIN, USER_MANAGER}

    @Id
    private Long id;
    private String name;
    @NotNull
    @Column(name = "id_num")
    private String idNum;

    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate dob;
    private Integer age;



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idNum='" + idNum + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
