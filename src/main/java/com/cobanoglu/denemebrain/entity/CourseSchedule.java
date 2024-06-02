package com.cobanoglu.denemebrain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_schedule")
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "hours", nullable = false)
    private String hours;

    public CourseSchedule() {}

    public CourseSchedule(Course course, String date, String hours) {
        this.course = course;
        this.date = date;
        this.hours = hours;
    }

    // Getter ve Setter metodları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
