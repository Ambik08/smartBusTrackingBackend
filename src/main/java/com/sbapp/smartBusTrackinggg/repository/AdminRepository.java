package com.sbapp.smartBusTrackinggg.repository;

import com.sbapp.smartBusTrackinggg.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByT(String t);
}
