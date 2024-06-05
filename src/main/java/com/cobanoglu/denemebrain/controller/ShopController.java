package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.*;
import com.cobanoglu.denemebrain.service.*;
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

    private final NotificationService notificationService;
    private final MeetingsService meetingsService;
    private HttpServletRequest request;
    @Autowired
    public ShopController(HttpServletRequest request,
                          CourseService courseService,
                          UserService userService,
                          TakenCourseService takenCourseService, MeetingsService meetingsService, NotificationService notificationService) {
        this.courseService = courseService;
        this.request = request;
        this.takenCourseService = takenCourseService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.meetingsService = meetingsService;
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

        List<CourseBuyingModel> cart = cartInSession.CartCourses;

        int totalPrice = calculateTotalPrice(cart);
        model.addAttribute("totalPrice", totalPrice);


        model.addAttribute("courses", cart); // Tüm kursları model'e ekle

        return "shop_page";
    }
    @PostMapping("/{id}/shop/add")
    public String AddToCart(@PathVariable("id") Long id,
                            @RequestParam("courseId") Long courseId,
                            @RequestParam(value = "currentDate", required = false) String date,
                            @RequestParam(value = "course_availablehours", required = false) String selectedHour,
                            Model model){
        HttpSession session = request.getSession();
        String cartKey = id.toString()+"cart";
        Cart cartInSession = new Cart();

        CourseBuyingModel courseToAddCart = new CourseBuyingModel();
        List<Course> courses = courseService.getAllCourses();
        for(var course : courses)
        {
            if(course.getId().equals(courseId))
            {
                courseToAddCart.setCourse(course);
                courseToAddCart.setDate(date);
                courseToAddCart.setHour(selectedHour);
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
    /*
    @GetMapping("/{id}/shop/add/{teacherId}")
    public String AddToCartFromTeacherPage(@PathVariable("id") Long id,
                                           @PathVariable("teacherId") Long teacherId,
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
        return "redirect:/user/home/" + id + "/teacher/" + teacherId;
    }
    */

    @PostMapping("/{id}/shop/remove")
    public String removeCourseFromCart(@PathVariable("id") Long id,
                                       @RequestParam("courseId") Long courseId,
                                       Model model) {
        HttpSession session = request.getSession();
        String cartKey = id.toString()+"cart";

        Cart cartToSession = (Cart) session.getAttribute(cartKey);

        cartToSession.CartCourses.removeIf(courseBuyingmodel -> courseBuyingmodel.getCourse().getId() == courseId);

        session.setAttribute(cartKey,cartToSession);

        List<CourseBuyingModel> cart = cartToSession.CartCourses;

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
                                Model model) {
        User user = userService.getUserById(id);
        HttpSession session = request.getSession();
        String CartKey = id.toString() + "cart";
        Cart cart = (Cart) session.getAttribute(CartKey);

        if (cardNumber.equals("4444 4444 4444 4444")
                && expiryDate.equals("12/27")
                && cvc.equals("444")) {
            for (CourseBuyingModel course : cart.getCartCourses()) {

                //TakenCourse Tablosuna Ekleme
                TakenCourse takenCourseToAdd = new TakenCourse();
                takenCourseToAdd.setCourse(course.getCourse());
                takenCourseToAdd.setUser(user);
                takenCourseService.SaveTakenCourse(takenCourseToAdd);

                //Meetings Tablosuna Ekleme
                Meetings meetings = new Meetings();
                meetings.setCourse(course.getCourse());
                meetings.setUser(user);
                meetings.setTeacher(course.getCourse().getTeacher());
                meetings.setDate(course.getDate());
                meetings.setHour(course.getHour());
                meetings.setLink(course.getDate().toString() + course.getHour().toString());
                meetingsService.createMeetings(meetings);

                // Bildirimi kaydet
                Notification notification = new Notification();
                notification.setCourse(course.getCourse());
                notification.setUser(user);
                notification.setTeacher(course.getCourse().getTeacher());
                notification.setHour(course.getHour());
                notification.setDate(course.getDate());
                notificationService.saveNotification(notification);
            }

            session.setAttribute(CartKey, null);
            return "redirect:/user/home/" + user.getId();
        } else {
            return "payment_screen";
        }
    }

    private int calculateTotalPrice(List<CourseBuyingModel> cart) {
        int totalPrice = 0;
        for (CourseBuyingModel c : cart) {
            totalPrice += c.getCourse().getPrice();
        }
        return totalPrice;
    }
}
