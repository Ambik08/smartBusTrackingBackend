package com.sbapp.smartBusTrackinggg.repository;

import com.sbapp.smartBusTrackinggg.entity.Route;
import com.sbapp.smartBusTrackinggg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

     User findByUserName(String userName);

     User findByT(String t);
}