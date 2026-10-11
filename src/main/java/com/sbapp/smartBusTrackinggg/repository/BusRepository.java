package com.sbapp.smartBusTrackinggg.repository;

import com.sbapp.smartBusTrackinggg.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {

    Bus findByBusNumber(String busNumber);

    Bus findByT(String t);
}