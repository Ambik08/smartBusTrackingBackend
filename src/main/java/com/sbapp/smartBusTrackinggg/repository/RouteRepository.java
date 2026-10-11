package com.sbapp.smartBusTrackinggg.repository;

import com.sbapp.smartBusTrackinggg.entity.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByUserId(Long userId);
    Route findByRouteNumber(String routeNumber);
    List<Route> findByUserOrganisationName(String organisationName);
}