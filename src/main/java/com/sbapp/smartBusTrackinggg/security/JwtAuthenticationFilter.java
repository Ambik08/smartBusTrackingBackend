package com.sbapp.smartBusTrackinggg.security;

import com.sbapp.smartBusTrackinggg.entity.Admin;
import com.sbapp.smartBusTrackinggg.entity.Bus;
import com.sbapp.smartBusTrackinggg.entity.User;
import com.sbapp.smartBusTrackinggg.repository.AdminRepository;
import com.sbapp.smartBusTrackinggg.repository.BusRepository;
import com.sbapp.smartBusTrackinggg.repository.UserRepository;
import com.sbapp.smartBusTrackinggg.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BusRepository busRepository;
    private final AdminRepository adminRepository;
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserRepository userRepository,BusRepository busRepository,AdminRepository adminRepository) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.busRepository=busRepository;
        this.adminRepository=adminRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String t = jwtService.extractT(token);

        User user = userRepository.findByT(t);
        String role="";

        if (user == null) {
            Bus bus=busRepository.findByT(t);
            if(bus==null) {
                Admin admin = adminRepository.findByT(t);
                if (admin == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                else {
                    role=admin.getRole();
                }
            }
            else {
                role=bus.getRole();
            }
        }
        else {
            role=user.getRole();
        }
        System.out.println("T = " + t);
        System.out.println("ROLE = " + role);
        System.out.println("AUTHORITY = ROLE_" + role);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        t,
                        null,
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + role
                                )
                        )
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        System.out.println(
                "AUTHENTICATED = " +
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .isAuthenticated()
        );

        System.out.println(
                "AUTHORITIES = " +
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getAuthorities()
        );

        System.out.println("🔥 BEFORE FILTER CHAIN");
        filterChain.doFilter(request, response);
        System.out.println("🔥 AFTER FILTER CHAIN");
    }
}
