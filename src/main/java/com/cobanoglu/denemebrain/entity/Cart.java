package com.cobanoglu.denemebrain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Setter;

import java.util.*;

@Setter
@Entity
public class Cart {


    public ArrayList<Course> CartCourses;
    private Long id;
    public Cart ()
    {
        CartCourses = new ArrayList<Course>();
    }

    @OneToMany
    public ArrayList<Course> getCartCourses() {return CartCourses;}

    @Id
    public Long getId() {
        return id;
    }
}
