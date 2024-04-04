package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionManager {
    public Teacher findLoggedInTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Oturumda saklanan öğretmen bilgisini al
            return (Teacher) session.getAttribute("loggedInTeacher");
        }
        return null;
    }

    public void setLoggedInTeacher(HttpServletRequest request, Teacher teacher) {
        HttpSession session = request.getSession(true);
        // Oturuma öğretmen bilgisini ekle
        session.setAttribute("loggedInTeacher", teacher);
    }
}

