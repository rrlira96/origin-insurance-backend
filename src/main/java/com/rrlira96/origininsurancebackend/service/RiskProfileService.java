package com.rrlira96.origininsurancebackend.service;

import com.rrlira96.origininsurancebackend.model.*;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.rrlira96.origininsurancebackend.model.InsurancePlan.*;
import static com.rrlira96.origininsurancebackend.model.MaritalStatus.married;
import static com.rrlira96.origininsurancebackend.model.OwnershipStatus.mortgaged;

@Service
public class RiskProfileService {

    public RiskProfile buildRiskProfile(User user) {
        RiskProfile riskProfile = new RiskProfile();
        Map<String, Integer> riskPointsMap = new HashMap<>();

        initializeRiskPointsMap(calculateBaseScore(user.getRiskAnswers()), riskPointsMap);

        updateScoreByAge(user.getAge(), riskPointsMap);
        updateScoreByIncome(user.getIncome(), riskPointsMap);
        updateScoreByHouse(user.getHouse(), riskPointsMap);
        updateScoreByDependents(user.getDependents(), riskPointsMap);
        updateScoreByMaritalStatus(user.getMaritalStatus(), riskPointsMap);
        updateScoreByVehicle(user.getVehicle(), riskPointsMap);

        setRiskForInsurances(riskPointsMap, riskProfile);
        setIneligibleInsurance(user, riskProfile);

        return riskProfile;
    }

    public static void updateScoreByVehicle(Vehicle vehicle, Map<String, Integer> riskPointsMap) {
        if (Objects.nonNull(vehicle) && Year.now().getValue() - vehicle.getYear() <= 5)
            riskPointsMap.merge("auto", 1, Integer::sum);
    }

    public static void updateScoreByMaritalStatus(MaritalStatus maritalStatus, Map<String, Integer> riskPointsMap) {
        if (maritalStatus.equals(married)) {
            riskPointsMap.merge("life", 1, Integer::sum);
            riskPointsMap.merge("disability", -1, Integer::sum);
        }
    }

    public static void updateScoreByDependents(int dependents, Map<String, Integer> riskPointsMap) {
        if (dependents > 0) {
            riskPointsMap.merge("life", 1, Integer::sum);
            riskPointsMap.merge("disability", 1, Integer::sum);
        }
    }

    public static void updateScoreByHouse(House house, Map<String, Integer> riskPointsMap) {
        if (Objects.nonNull(house) && house.getOwnershipStatus().equals(mortgaged)) {
            riskPointsMap.merge("home", 1, Integer::sum);
            riskPointsMap.merge("disability", 1, Integer::sum);
        }
    }

    public static void updateScoreByIncome(int income, Map<String, Integer> riskPointsMap) {
        if (income > 200000) riskPointsMap.replaceAll((k, v) -> v - 1);
    }

    public static void updateScoreByAge(int age, Map<String, Integer> riskPointsMap) {
        if (age <= 40) {
            if (age < 30) {
                riskPointsMap.replaceAll((k, v) -> v - 2);
            } else {
                riskPointsMap.replaceAll((k, v) -> v - 1);
            }
        }
    }

    private void setRiskForInsurances(Map<String, Integer> riskPointsMap, RiskProfile riskProfile) {
        riskProfile.setLife(calculateInsurancePlan(riskPointsMap.get("life")));
        riskProfile.setHome(calculateInsurancePlan(riskPointsMap.get("home")));
        riskProfile.setDisability(calculateInsurancePlan(riskPointsMap.get("disability")));
        riskProfile.setAuto(calculateInsurancePlan(riskPointsMap.get("auto")));
    }

    public static InsurancePlan calculateInsurancePlan(Integer score) {
        if (score >= 3) {
            return responsible;
        } else if (score >= 1) {
            return regular;
        } else {
            return economic;
        }
    }

    public static int calculateBaseScore(List<Integer> riskAnswers) {
        return riskAnswers.stream().mapToInt(Integer::intValue).sum();
    }

    private void initializeRiskPointsMap(int baseScore, Map<String, Integer> riskPointsMap) {
        riskPointsMap.put("auto", baseScore);
        riskPointsMap.put("disability", baseScore);
        riskPointsMap.put("home", baseScore);
        riskPointsMap.put("life", baseScore);
    }

    public static void setIneligibleInsurance(User user, RiskProfile riskProfile) {
        if (user.getIncome() == 0 || user.getAge() > 60) riskProfile.setDisability(ineligible);
        if (user.getAge() > 60) riskProfile.setLife(ineligible);
        if (Objects.isNull(user.getVehicle())) riskProfile.setAuto(ineligible);
        if (Objects.isNull(user.getHouse())) riskProfile.setHome(ineligible);
    }

}
