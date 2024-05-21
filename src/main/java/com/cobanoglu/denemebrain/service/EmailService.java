package com.cobanoglu.denemebrain.service;

public interface EmailService {
    public void sendEmail(String to, String subject, String text);
}
