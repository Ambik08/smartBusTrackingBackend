package com.sbapp.smartBusTrackinggg.controller;

import com.sbapp.smartBusTrackinggg.entity.Bus;
import com.sbapp.smartBusTrackinggg.entity.Route;
import com.sbapp.smartBusTrackinggg.service.BusService;
import com.sbapp.smartBusTrackinggg.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private RouteService routeService;


    // Add Bus To Route
    @PostMapping("/{routeNumber}")
    public ResponseEntity<?> addBus(
            @PathVariable String routeNumber,
            @RequestBody Bus bus) {

        try {

            if (bus == null ||
                    bus.getBusNumber() == null ||
                    bus.getBusNumber().isBlank()) {

                return new ResponseEntity<>(
                        "Bus number is required",
                        HttpStatus.BAD_REQUEST
                );
            }

            Route route = routeService.getRoute(routeNumber);

            if (route == null) {

                return new ResponseEntity<>(
                        "Route not found",
                        HttpStatus.NOT_FOUND
                );
            }

            Bus existingBus =
                    busService.findBusById(bus.getBusNumber());

            if (existingBus != null) {

                return new ResponseEntity<>(
                        "Bus already exists",
                        HttpStatus.CONFLICT
                );
            }

            // Maintain both sides
            route.addBus(bus);

            busService.add(bus);

            return new ResponseEntity<>(
                    "Bus added successfully",
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            System.out.println(
                    "Error in adding bus : " + e
            );

            return new ResponseEntity<>(
                    "Error in adding bus",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Delete Bus
    @DeleteMapping("/{busNumber}")
    public ResponseEntity<?> deleteBus(
            @PathVariable String busNumber) {

        try {

            Bus bus =
                    busService.findBusById(busNumber);

            if (bus == null) {

                return new ResponseEntity<>(
                        "Bus not found",
                        HttpStatus.NOT_FOUND
                );
            }

            busService.delete(bus);

            return new ResponseEntity<>(
                    "Bus deleted successfully",
                    HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error in deleting bus",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Update Bus Location
    @PutMapping("/location")
    public ResponseEntity<?> updateLocation(
            @RequestBody Bus busLocation) {

        try {

            Bus bus =
                    busService.findBusByIdd(
                            busLocation.getId()
                    );

            if (bus == null) {

                return new ResponseEntity<>(
                        "Bus not found",
                        HttpStatus.NOT_FOUND
                );
            }

            busService.updateLoc(busLocation);

            return new ResponseEntity<>(
                    "Bus location updated successfully",
                    HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error in updating bus location",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Bus Login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Bus bus) {

        if (bus == null ||
                bus.getBusNumber() == null ||
                bus.getPassword() == null ||
                bus.getBusNumber().isBlank() ||
                bus.getPassword().isBlank()) {

            return new ResponseEntity<>(
                    "All fields are required",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (!busService.chkBusNumber(bus.getBusNumber())) {

            return new ResponseEntity<>(
                    "Bus number doesn't exist",
                    HttpStatus.NOT_FOUND
            );
        }

        if (!busService.chkBus(
                bus.getBusNumber(),
                bus.getPassword())) {

            return new ResponseEntity<>(
                    "Incorrect bus number or password",
                    HttpStatus.UNAUTHORIZED
            );
        }
        Bus b = busService.findBusById(bus.getBusNumber());
        String token=busService.getJWT(b);
        Map<String, Object> response = new HashMap<>();
        response.put("id", b.getId());
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Get Bus By ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBusById(
            @PathVariable Long id) {

        Bus bus = busService.findBusByIdd(id);

        if (bus == null) {

            return new ResponseEntity<>(
                    "Bus not found",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                bus,
                HttpStatus.OK
        );
    }


    // End Location
    @PutMapping("/busLoc/{id}")
    public ResponseEntity<?> endLocation(
            @PathVariable Long id) {

        boolean result =
                busService.endLoc(id);

        if (result) {

            return new ResponseEntity<>(
                    "Location set to null",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                "Bus not found",
                HttpStatus.NOT_FOUND
        );
    }


    // Get All Buses
    @GetMapping
    public ResponseEntity<?> getBuses() {

        List<Bus> buses =
                busService.getBusNames();

        if (buses.isEmpty()) {

            return new ResponseEntity<>(
                    "No buses found",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                buses,
                HttpStatus.OK
        );
    }
}