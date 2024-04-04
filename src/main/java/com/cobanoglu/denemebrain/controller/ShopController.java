package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CourseService;
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
    private final List<Course> cart;
    @Autowired
    public ShopController(CourseService courseService, List<Course> cart) {
        this.courseService = courseService;
        this.cart = new ArrayList<>();
    }



    @GetMapping("{id}/shop")
    public String showShopPage(@PathVariable("id") Long id,
                               @RequestParam("courseId") Long courseId,
                               Model model){

        Course course = courseService.findById(courseId);
        if (!isCourseInCart(course)) {
            cart.add(course); // Yeni kursu sepete ekle
        }
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
        Course course = courseService.findById(courseId);
        cart.remove(course); // Sepetten kursu kaldır
        int totalPrice = calculateTotalPrice(cart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("courses", cart); // Tüm kursları modele ekle
        return "redirect:/user/home/" + id + "/shop?id=" + id + "&courseId=" + courseId; // Sepet sayfasına geri yönlendir ve hem id hem de courseId parametrelerini ekle

    }


    private boolean isCourseInCart(Course course) {
        for (Course c : cart) {
            if (c.getId().equals(course.getId())) {
                return true; // Kurs sepette bulundu
            }
        }
        return false; // Kurs sepette bulunamadı
    }

    private int calculateTotalPrice(List<Course> cart) {
        int totalPrice = 0;
        for (Course c : cart) {
            totalPrice += c.getPrice();
        }
        return totalPrice;
    }
}
