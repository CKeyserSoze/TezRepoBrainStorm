package com.cobanoglu.denemebrain.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "taken_courses")
public class TakenCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taken_courses_id")
    private Long takenCoursesId;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getTakenCoursesId() {
        return takenCoursesId;
    }

    public void setTakenCoursesId(Long takenCoursesId) {
        this.takenCoursesId = takenCoursesId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TakenCourse(Long takenCoursesId, Course course, User user) {
        this.takenCoursesId = takenCoursesId;
        this.course = course;
        this.user = user;
    }

    public TakenCourse() {
    }

}
