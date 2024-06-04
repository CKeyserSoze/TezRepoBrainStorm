package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Cart;
import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
@Controller
@RequestMapping("/user/cart")
public class CartController {

    private CourseService _courseService;
    private HttpServletRequest _request;
    private UserService _userService;

    public CartController(UserService userService,CourseService _courseService, HttpServletRequest request) {
        this._courseService = _courseService;
        this._request = request;
        this._userService = userService;
    }

    @GetMapping("/{id}")
    public String CartPage(@PathVariable("id") Long id,
                               @RequestParam("courseId") Long courseId,
                               Model model){
        String cartKey = id.toString()+"cart";

        HttpSession session = _request.getSession();
        if(session.getAttribute(cartKey) != null)
        {

        }
        Cart cart = (Cart)session.getAttribute(cartKey);

        model.addAttribute(cartKey,cart);
        User user =  _userService.getUserById(id);
        model.addAttribute("user", user);
        return "shop_page";
    }
    @PostMapping("/{id}")
    public void AddCourseToCart(@PathVariable("id") Long id,
                     @RequestParam("courseId") Long courseId,
                     Model model){
        String cartKey = id.toString()+"cart";
        Course courseToAddCart = new Course();
        List<Course> courses = _courseService.getAllCourses();
        for (var course :courses)
        {
            if(course.getId() == courseId)
            {
                courseToAddCart = course;
            }
        }
        HttpSession session = _request.getSession();

        if(session.getAttribute(cartKey) != null)
        {
            Cart cart = new Cart();
            cart.CartCourses.add(courseToAddCart);
            session.setAttribute(cartKey,cart);
        }
        else
        {
            Cart cart = (Cart) session.getAttribute(cartKey);
            cart.CartCourses.add(courseToAddCart);
            session.setAttribute(cartKey,cart);
        }

        User user =  _userService.getUserById(id);
        model.addAttribute("user", user);
    }
}*/
