package com.cobanoglu.denemebrain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id",nullable = false)
    private Long id;

    @Column(name = "teacher_name", nullable = false)
    private String name;

    @Column(name = "teacher_surname", nullable = false)
    private String surname;

    @Column(name = "teacher_mail", unique = true, nullable = false)
    private String email;

    @Column(name = "teacher_password", nullable = false)
    private String password;

    @Column(name = "teacher_education_level", nullable = false)
    private String educationLevel;

    @Column(name = "teacher_information")
    private String teacherInformation;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;

    @Column(name = "verification_token", unique = true)
    private String verificationToken;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getTeacherInformation() {
        return teacherInformation;
    }

    public void setTeacherInformation(String teacherInformation) {
        this.teacherInformation = teacherInformation;
    }

    public Teacher(String firstName, String lastName, String email, String password, String education) {
        this.name = firstName;
        this.surname = lastName;
        this.email = email;
        this.password = password;
        this.educationLevel = education;
    }

    public Teacher() {

    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
}
