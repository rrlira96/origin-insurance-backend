package com.rrlira96.origininsurancebackend.controller;

import com.rrlira96.origininsurancebackend.model.User;
import com.rrlira96.origininsurancebackend.model.RiskProfile;
import com.rrlira96.origininsurancebackend.service.RiskProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/risk-profiles")
public class RiskProfileController {

    @Autowired
    private RiskProfileService riskProfileService;

    @PostMapping
    public ResponseEntity<RiskProfile> generateRiskProfile(@RequestBody @Valid User user) {
        return ResponseEntity.ok().body(riskProfileService.buildRiskProfile(user));
    }

}
