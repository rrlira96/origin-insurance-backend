package com.rrlira96.origininsurancebackend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskProfile {
    private InsurancePlan auto;
    private InsurancePlan disability;
    private InsurancePlan home;
    private InsurancePlan life;
}
