package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Cart;
import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.TakenCourse;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TakenCourseService;
import com.cobanoglu.denemebrain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/home")
public class ShopController {

    private final UserService userService;
    private final CourseService courseService;
    private final TakenCourseService takenCourseService;
    private HttpServletRequest request;
    @Autowired
    public ShopController(HttpServletRequest request,
                          CourseService courseService,
                          UserService userService,
                          TakenCourseService takenCourseService) {
        this.courseService = courseService;
        this.request = request;
        this.takenCourseService = takenCourseService;
        this.userService = userService;
    }



    @GetMapping("{id}/shop")
    public String showShopPage(@PathVariable("id") Long id,
                               Model model){
        HttpSession session = request.getSession();
        String cartKey = id.toString()+"cart";
        Cart cartInSession = new Cart();

        if(session.getAttribute(cartKey) == null)
        {
            session.setAttribute(cartKey,cartInSession);
        }
        else
        {
            cartInSession = (Cart) session.getAttribute(cartKey);
        }
        /*
        //Eklenecek Course'u bulma
        Course courseToAddCart = new Course();
        List<Course> courses = courseService.getAllCourses();
        for(var course : courses)
        {
            if(course.getId() == courseId)
            {
                courseToAddCart = course;
            }
        }
        if(courseId == 10000)
        {
            cartInSession = (Cart) session.getAttribute(cartKey);
        }
        else if(session.getAttribute(cartKey) == null)
        {
            cartInSession.CartCourses.add(courseToAddCart);
            session.setAttribute(cartKey,cartInSession);
        }
        else {
            cartInSession = (Cart) session.getAttribute(cartKey);
            cartInSession.CartCourses.add(courseToAddCart);
            session.setAttribute(cartKey,cartInSession);
        }
        */
        List<Course> cart = cartInSession.CartCourses;

        int totalPrice = calculateTotalPrice(cart);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("courses", cart); // Tüm kursları model'e ekle
        return "shop_page";
    }
    @GetMapping("/{id}/shop/add")
    public String AddToCart(@PathVariable("id") Long id,
                                                      @RequestParam("courseId") Long courseId,
                                                      Model model){
        HttpSession session = request.getSession();
        String cartKey = id.toString()+"cart";
        Cart cartInSession = new Cart();

        Course courseToAddCart = new Course();
        List<Course> courses = courseService.getAllCourses();
        for(var course : courses)
        {
            if(course.getId() == courseId)
            {
                courseToAddCart = course;
            }
        }

        if(session.getAttribute(cartKey) == null)
        {
            cartInSession.CartCourses.add(courseToAddCart);
            session.setAttribute(cartKey,cartInSession);
        }
        else {
            cartInSession = (Cart) session.getAttribute(cartKey);
            cartInSession.CartCourses.add(courseToAddCart);
            session.setAttribute(cartKey,cartInSession);
        }
        return "redirect:/user/home/" + id;
    }
    @PostMapping("/{id}/shop/remove")
    public String removeCourseFromCart(@PathVariable("id") Long id,
                                       @RequestParam("courseId") Long courseId,
                                       Model model) {
        HttpSession session = request.getSession();
        String cartKey = id.toString()+"cart";

        Cart cartToSession = (Cart) session.getAttribute(cartKey);

        cartToSession.CartCourses.removeIf(course -> course.getId() == courseId);

        session.setAttribute(cartKey,cartToSession);

        List<Course> cart = cartToSession.CartCourses;

        int totalPrice = calculateTotalPrice(cart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("courses", cart); // Tüm kursları modele ekle
        return "redirect:/user/home/" + id + "/shop?id=" + id; // Sepet sayfasına geri yönlendir ve hem id hem de courseId parametrelerini ekle

    }
    @GetMapping("{id}/payment")
    public String Payment(@PathVariable("id") Long id,
                                       Model model){

        return "payment_screen";
    }

    @PostMapping("{id}/payment")
    public String PaymentFinish(@PathVariable("id") Long id,
                                @RequestParam("cardNumber") String cardNumber,
                                @RequestParam("expiryDate") String expiryDate,
                                @RequestParam("cvc") String cvc,
                                Model model){
        User user = userService.getUserById(id);
        HttpSession session = request.getSession();
        String CartKey = id.toString()+"cart";
        Cart cart = (Cart) session.getAttribute(CartKey);


        if(cardNumber.equals("4444 4444 4444 4444")
            && expiryDate.equals("12/27")
            && cvc.equals("444"))
        {
            for(var Course :cart.CartCourses)
            {
                TakenCourse takenCourseToAdd = new TakenCourse();
                takenCourseToAdd.setCourse(Course);
                takenCourseToAdd.setUser(user);
                takenCourseService.SaveTakenCourse(takenCourseToAdd);
            }
            return "redirect:/user/home/" + user.getId();
        }else
        {
            return "payment_screen";
        }
    }

/*
    private boolean isCourseInCart(Course course) {
        for (Course c : cart) {
            if (c.getId().equals(course.getId())) {
                return true; // Kurs sepette bulundu
            }
        }
        return false; // Kurs sepette bulunamadı
    }
*/
    private int calculateTotalPrice(List<Course> cart) {
        int totalPrice = 0;
        for (Course c : cart) {
            totalPrice += c.getPrice();
        }
        return totalPrice;
    }
}
