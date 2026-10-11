package com.sbapp.smartBusTrackinggg.controller;

import com.sbapp.smartBusTrackinggg.entity.Route;
import com.sbapp.smartBusTrackinggg.service.RouteService;
import com.sbapp.smartBusTrackinggg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;
    private final UserService userService;

    public RouteController(
            RouteService routeService,
            UserService userService) {

        this.routeService = routeService;
        this.userService = userService;
    }


    // Add Route
    @PostMapping("/{username}")
    public ResponseEntity<?> addRoute(
            @PathVariable String username,
            @RequestBody Route route) {
        System.out.println("Username received: " + username);
        System.out.println( "Username exists: " + userService.chkUsername(username));

        try {

            if (!userService.chkUsername(username)) {

                return new ResponseEntity<>(
                        "Unable to add route",
                        HttpStatus.NOT_FOUND
                );
            }

            if (route == null ||
                    route.getRouteNumber() == null ||
                    route.getRouteNumber().isBlank()) {

                return new ResponseEntity<>(
                        "Please add route number",
                        HttpStatus.BAD_REQUEST
                );
            }

            if (route.getSource() == null ||
                    route.getDestination() == null ||
                    route.getSource().isBlank() ||
                    route.getDestination().isBlank()) {

                return new ResponseEntity<>(
                        "Please include source and destination",
                        HttpStatus.BAD_REQUEST
                );
            }

            if (route.getSource()
                    .equalsIgnoreCase(route.getDestination())) {

                return new ResponseEntity<>(
                        "Source and Destination cannot be same",
                        HttpStatus.BAD_REQUEST
                );
            }

            if (routeService.isRouteExistss(
                    route.getRouteNumber())) {

                return new ResponseEntity<>(
                        "Route already exists",
                        HttpStatus.CONFLICT
                );
            }

            routeService.addRoute(route,username);

            return new ResponseEntity<>(
                    "Route successfully created",
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            System.out.println(
                    "Error in addRoute : " + e
            );

            return new ResponseEntity<>(
                    "Error in adding route",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Update Route
    @PutMapping("/{routeNumber}")
    public ResponseEntity<?> updateRoute(
            @PathVariable String routeNumber,
            @RequestBody Route route) {

        if (!routeService.isRouteExistss(routeNumber)) {

            return new ResponseEntity<>(
                    "Route doesn't exist",
                    HttpStatus.NOT_FOUND
            );
        }

        if (route == null ||
                route.getSource() == null ||
                route.getDestination() == null ||
                route.getSource().isBlank() ||
                route.getDestination().isBlank()) {

            return new ResponseEntity<>(
                    "Please include source and destination",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (route.getSource()
                .equalsIgnoreCase(route.getDestination())) {

            return new ResponseEntity<>(
                    "Source and Destination cannot be same",
                    HttpStatus.BAD_REQUEST
            );
        }

        Route existingRoute =
                routeService.getRoute(routeNumber);

        boolean updated =
                routeService.updateRoute(
                        existingRoute,
                        route
                );

        if (updated) {

            return new ResponseEntity<>(
                    "Route successfully updated",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                "Error in updating route",
                HttpStatus.BAD_REQUEST
        );
    }


    // Get Route
    @GetMapping("/{routeNumber}")
    public ResponseEntity<?> getByRouteNumber(
            @PathVariable String routeNumber) {

        Route route =
                routeService.getRoute(routeNumber);

        if (route == null) {

            return new ResponseEntity<>(
                    "Route doesn't exist",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                route,
                HttpStatus.OK
        );
    }


    // Delete Route
    @DeleteMapping("/{routeNumber}")
    public ResponseEntity<?> deleteByRouteNumber(
            @PathVariable String routeNumber) {

        boolean deleted =
                routeService.deleteRouteNumber(routeNumber);

        if (!deleted) {

            return new ResponseEntity<>(
                    "Route doesn't exist",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                "Route deleted successfully",
                HttpStatus.OK
        );
    }


    // Get All Routes
    @GetMapping("/a/{orgName}")
    public ResponseEntity<?> getAllRoutes(@PathVariable String orgName) {

        List<Route> routes =
                routeService.getAllRoutes(orgName);

        if (routes.isEmpty()) {

            return new ResponseEntity<>(
                    //no routes found
                    "NRF",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                routes,
                HttpStatus.OK
        );
    }

    @GetMapping("/org/{orgName}")
    public ResponseEntity<?> getRoutesByOrganisation(
            @PathVariable String orgName) {

        try {
            List<Route> routes = routeService.getRoutesByOrganisation(orgName);

            if (routes.isEmpty()) {
                return new ResponseEntity<>("No routes found for this organisation",
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(routes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching routes",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
