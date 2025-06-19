package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.MembershipPackage;
import com.example.SWP_Backend.repository.MembershipPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipPackageService {
    @Autowired
    private MembershipPackageRepository repository;

    public List<MembershipPackage> getAllPackages() {
        return repository.findAll();
    }}
