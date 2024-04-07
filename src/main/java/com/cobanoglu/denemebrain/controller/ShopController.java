package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Cart;
import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CourseService;
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

    private final CourseService courseService;
    private HttpServletRequest request;
    @Autowired
    public ShopController(HttpServletRequest request,CourseService courseService) {
        this.courseService = courseService;
        this.request = request;
    }



    @GetMapping("{id}/shop")
    public String showShopPage(@PathVariable("id") Long id,
                               @RequestParam("courseId") Long courseId,
                               Model model){
        HttpSession session = request.getSession();
        String cartKey = id.toString()+"cart";
        Cart cartInSession = new Cart();

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

        List<Course> cart = cartInSession.CartCourses;

        int totalPrice = calculateTotalPrice(cart);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("courses", cart); // Tüm kursları model'e ekle
        model.addAttribute("courseId", courseId);
        return "shop_page";
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
        return "redirect:/user/home/" + id + "/shop?id=" + id + "&courseId=" + 10000; // Sepet sayfasına geri yönlendir ve hem id hem de courseId parametrelerini ekle

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
