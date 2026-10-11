
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

        try {
            System.out.println("Organisation received: " + username);

            if (!userService.chkOrganisationName(username)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Organisation not found");
            }

            if (route == null ||
                    route.getRouteNumber() == null ||
                    route.getRouteNumber().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Please add route number");
            }

            if (route.getSource() == null ||
                    route.getDestination() == null ||
                    route.getSource().isBlank() ||
                    route.getDestination().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Please include source and destination");
            }

            if (route.getSource().trim()
                    .equalsIgnoreCase(route.getDestination().trim())) {
                return ResponseEntity.badRequest()
                        .body("Source and Destination cannot be same");
            }

            route.setRouteNumber(route.getRouteNumber().trim());
            route.setSource(route.getSource().trim());
            route.setDestination(route.getDestination().trim());

            if (routeService.isRouteExistss(route.getRouteNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Route already exists");
            }

            routeService.addRoute(route, username);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Route successfully created");

        } catch (Exception e) {
            System.err.println("Error in addRoute:");
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in adding route");
        }
    }

    // Update Route
    @PutMapping("/{routeNumber}")
    public ResponseEntity<?> updateRoute(
            @PathVariable String routeNumber,
            @RequestBody Route route) {

        try {
            if (!routeService.isRouteExistss(routeNumber)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Route doesn't exist");
            }

            if (route == null ||
                    route.getSource() == null ||
                    route.getDestination() == null ||
                    route.getSource().isBlank() ||
                    route.getDestination().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Please include source and destination");
            }

            if (route.getSource().trim()
                    .equalsIgnoreCase(route.getDestination().trim())) {
                return ResponseEntity.badRequest()
                        .body("Source and Destination cannot be same");
            }

            Route existingRoute = routeService.getRoute(routeNumber);

            if (existingRoute == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Route doesn't exist");
            }

            boolean updated =
                    routeService.updateRoute(existingRoute, route);

            if (updated) {
                return ResponseEntity.ok("Route successfully updated");
            }

            return ResponseEntity.badRequest()
                    .body("Error in updating route");

        } catch (Exception e) {
            System.err.println("Error in updateRoute:");
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in updating route");
        }
    }

    // Get Route by Route Number
    @GetMapping("/{routeNumber}")
    public ResponseEntity<?> getByRouteNumber(
            @PathVariable String routeNumber) {

        try {
            Route route = routeService.getRoute(routeNumber);

            if (route == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Route doesn't exist");
            }

            return ResponseEntity.ok(route);

        } catch (Exception e) {
            System.err.println("Error in getByRouteNumber:");
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching route");
        }
    }

    // Delete Route
    @DeleteMapping("/{routeNumber}")
    public ResponseEntity<?> deleteByRouteNumber(
            @PathVariable String routeNumber) {

        try {
            boolean deleted =
                    routeService.deleteRouteNumber(routeNumber);

            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Route doesn't exist");
            }

            return ResponseEntity.ok("Route deleted successfully");

        } catch (Exception e) {
            System.err.println("Error in deleteByRouteNumber:");
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting route");
        }
    }

    // Get All Routes for an Organisation
    @GetMapping("/a/{orgName}")
    public ResponseEntity<?> getAllRoutes(
            @PathVariable String orgName) {

        try {
            System.out.println("Organisation received for GET: " + orgName);

            List<Route> routes =
                    routeService.getRoutesByOrganisation(orgName);

            System.out.println("Routes found: " + routes.size());

            if (routes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("NRF");
            }

            return ResponseEntity.ok(routes);

        } catch (Exception e) {
            System.err.println("Error in getAllRoutes:");
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching routes");
        }
    }

    // Get Routes by Organisation
    @GetMapping("/org/{orgName}")
    public ResponseEntity<?> getRoutesByOrganisation(
            @PathVariable String orgName) {

        try {
            List<Route> routes =
                    routeService.getRoutesByOrganisation(orgName);

            if (routes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No routes found for this organisation");
            }

            return ResponseEntity.ok(routes);

        } catch (Exception e) {
            System.err.println("Error in getRoutesByOrganisation:");
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching routes");
        }
    }
}
