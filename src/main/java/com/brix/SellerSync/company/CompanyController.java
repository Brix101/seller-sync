package com.brix.SellerSync.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brix.SellerSync.company.service.CompanyService;

@RestController
public class CompanyController {

    private final CompanyService companyService;


    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(path = "/companies")
    public ResponseEntity<List<Company>> company() {
        return new ResponseEntity<List<Company>>(companyService.listCompanies(), HttpStatus.OK);
    }
}
