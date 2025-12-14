package vn.gt.__back_end_javaspring.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class UserDTO { // Check

    private String id;


    private String fullName;


    private String phone;


    private String email;



    private LocalDate dateOfBirth;

   
    private String address;


    private String avatar;


    Integer followerCount;

   
    Integer followingCount;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    private String vertifiedBank;





}
