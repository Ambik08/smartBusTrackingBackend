package com.sbapp.smartBusTrackinggg.service;

import com.sbapp.smartBusTrackinggg.entity.Bus;
import com.sbapp.smartBusTrackinggg.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public BusService(BusRepository busRepository,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.passwordEncoder=passwordEncoder;
        this.busRepository=busRepository;
        this.jwtService=jwtService;
    }


    // Add Bus
    public Bus add(Bus bus) {
        bus.setPassword(passwordEncoder.encode(bus.getPassword()));
        bus.setT(bus.getBusNumber());
        return busRepository.save(bus);
    }


    // Check Bus Login
    public boolean chkBus(String busNumber, String password) {

        Bus bus = busRepository.findByBusNumber(busNumber);

        if (bus == null) {
            return false;
        }

        return passwordEncoder.matches(password,bus.getPassword());
    }


    // Find Bus By Bus Number
    public Bus findBusById(String busNumber) {
        return busRepository.findByBusNumber(busNumber);
    }


    // Delete Bus
    @Transactional
    public void delete(Bus bus) {
        busRepository.delete(bus);
    }


    // Update Location
    public void updateLoc(Bus busLocation) {

        Bus bus = busRepository
                .findById(busLocation.getId())
                .orElse(null);

        if (bus == null) {
            return;
        }

        bus.setLatitude(busLocation.getLatitude());
        bus.setLongitude(busLocation.getLongitude());

        busRepository.save(bus);
    }


    // Check Bus Number
    public boolean chkBusNumber(String busNumber) {
        return busRepository.findByBusNumber(busNumber) != null;
    }


    // Get Bus ID
    public Long getBusId(String busNumber) {

        Bus bus = busRepository.findByBusNumber(busNumber);

        if (bus == null) {
            return null;
        }

        return bus.getId();
    }


    // Find Bus By ID
    public Bus findBusByIdd(Long id) {
        return busRepository.findById(id).orElse(null);
    }


    // End Location
    @Transactional
    public boolean endLoc(Long id) {

        Bus bus = busRepository.findById(id).orElse(null);

        if (bus == null) {
            return false;
        }

        bus.setLatitude(null);
        bus.setLongitude(null);

        busRepository.save(bus);

        return true;
    }


    // Get All Buses
    public List<Bus> getBusNames() {
        return busRepository.findAll();
    }

    public String getJWT(Bus b) {
        return  jwtService.generateToken(b.getT(),b.getRole());
    }
}