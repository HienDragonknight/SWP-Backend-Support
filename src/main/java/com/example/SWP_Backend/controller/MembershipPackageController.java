package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.entity.MembershipPackage;
import com.example.SWP_Backend.service.MembershipPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")

public class MembershipPackageController {

    @Autowired
    private MembershipPackageService service;

    @GetMapping
    public List<MembershipPackage> getAllPackages() {
        return service.getAllPackages();
    }
}
