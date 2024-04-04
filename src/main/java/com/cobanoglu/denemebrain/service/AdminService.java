package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(Long id);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Admin admin);
    void deleteAdminById(Long id);

}
