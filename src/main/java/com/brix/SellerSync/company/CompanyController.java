package com.brix.SellerSync.company;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @GetMapping(path = "/company")
    public String company() {
        return "company";
    }
}
