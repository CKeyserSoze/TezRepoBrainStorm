package com.cobanoglu.denemebrain.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String name;

    @Column(name = "course_description", nullable = false)
    private String description;

    @Column(name = "course_price", nullable = false)
    private int price;

    @Column(name = "course_image")
    private String image = "/static/images/course-image2.jpg";

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "course_rating")
    private float rating;

    @Column(name = "course_grade", nullable = false)
    private String grade;

    @Column(name = "course_lesson", nullable = false)
    private String lesson;


    public Course() {
    }

    public Course(Long id, String name, String description, int price, String image, float rating, String grade, String lesson, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.teacher = teacher;
        this.rating = rating;
        this.grade = grade;
        this.lesson = lesson;
    }

    public Course(String courseName, String description, int price, String image, String grade, String lesson, Teacher teacher) {
        this.name = courseName;
        this.description = description;
        this.price = price;
        this.image = image;
        this.grade = grade;
        this.lesson = lesson;
        this.teacher = teacher;
    }

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
}
