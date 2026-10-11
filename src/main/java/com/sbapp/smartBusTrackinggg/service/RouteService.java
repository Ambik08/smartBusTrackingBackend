package com.sbapp.smartBusTrackinggg.service;

import com.sbapp.smartBusTrackinggg.entity.Route;
import com.sbapp.smartBusTrackinggg.entity.User;
import com.sbapp.smartBusTrackinggg.repository.RouteRepository;
import com.sbapp.smartBusTrackinggg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private  final RouteRepository routeRepository;
    private final UserRepository userRepository;
    public RouteService(RouteRepository routeRepository,UserRepository userRepository){
        this.routeRepository=routeRepository;
        this.userRepository=userRepository;
    }




    // Check Route Exists
    public boolean isRouteExistss(String routeNumber) {

        Route r= routeRepository
                .findByRouteNumber(routeNumber);
        return r!=null;
    }


    // Add Route
    public boolean addRoute(Route route,String username) {

        if (route.getBuses() == null) {
            route.setBuses(new java.util.ArrayList<>());
        }
        User user= userRepository.findByUserName(username);
        route.setUser(user);

        routeRepository.save(route);

        return true;
    }


    // Get Route
    public Route getRoute(String routeNumber) {

        return routeRepository
                .findByRouteNumber(routeNumber);
    }


    // Delete Route
    public boolean deleteRouteNumber(String routeNumber) {

        Route route = routeRepository
                .findByRouteNumber(routeNumber);

        if (route == null) {
            return false;
        }

        routeRepository.delete(route);

        return true;
    }


    // Update Route
    public boolean updateRoute(
            Route existingRoute,
            Route route) {

        if (existingRoute == null) {
            return false;
        }

        existingRoute.setSource(route.getSource());
        existingRoute.setDestination(route.getDestination());

        routeRepository.save(existingRoute);

        return true;
    }


    public List<Route> getAllRoutes(String orgName) {
        User user = userRepository.findByUserName(orgName);
        return routeRepository.findByUserId(user.getId());
    }

    public List<Route> getRoutesByOrganisation(String orgName) {
        return routeRepository.findByUserOrganisationName(orgName);
    }
}