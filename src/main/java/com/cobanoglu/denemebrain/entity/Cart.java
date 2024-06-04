package com.cobanoglu.denemebrain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Setter;

import java.util.*;

@Setter
public class Cart {


    public ArrayList<CourseBuyingModel> CartCourses;
    private Long id;
    public Cart ()
    {
        CartCourses = new ArrayList<CourseBuyingModel>();
    }

    @OneToMany
    public ArrayList<CourseBuyingModel> getCartCourses() {return CartCourses;}

    @Id
    public Long getId() {
        return id;
    }
}
