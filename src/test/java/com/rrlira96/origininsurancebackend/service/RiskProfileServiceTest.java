package com.rrlira96.origininsurancebackend.service;

import com.rrlira96.origininsurancebackend.model.*;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rrlira96.origininsurancebackend.model.InsurancePlan.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskProfileServiceTest {

    private final Map<String, Integer> riskPointsMap = new HashMap<>();

    @Test
    void givenVehicleYearInLastFiveYears_whenBuildRiskProfile_thenIncreaseOneAutoRiskPoint() {
        // Given
        riskPointsMap.put("auto", 0);
        int year = Year.now().getValue() - 2;
        Vehicle vehicle = new Vehicle(year);

        // When
        RiskProfileService.updateScoreByVehicle(vehicle, riskPointsMap);
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(1, autoScore);
    }

    @Test
    void givenVehicleYearMoreThanLastFiveYears_whenBuildRiskProfile_thenDoNothing() {
        // Given
        riskPointsMap.put("auto", 0);
        int year = Year.now().getValue() - 7;
        Vehicle vehicle = new Vehicle(year);

        // When
        RiskProfileService.updateScoreByVehicle(vehicle, riskPointsMap);
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(0, autoScore);
    }

    @Test
    void givenMarriedUser_whenBuildRiskProfile_thenIncreaseOneLifeRiskPointAndDecreaseOneDisabilityRiskPoint() {
        // Given
        riskPointsMap.put("life", 0);
        riskPointsMap.put("disability", 0);

        // When
        RiskProfileService.updateScoreByMaritalStatus(MaritalStatus.married, riskPointsMap);
        int lifeScore = riskPointsMap.get("life");
        int disabilityScore = riskPointsMap.get("disability");

        // Then
        assertEquals(1, lifeScore);
        assertEquals(-1, disabilityScore);

    }

    @Test
    void givenSingleUser_whenBuildRiskProfile_thenDoNothing() {
        // Given
        riskPointsMap.put("life", 0);
        riskPointsMap.put("disability", 0);

        // When
        RiskProfileService.updateScoreByMaritalStatus(MaritalStatus.single, riskPointsMap);
        int lifeScore = riskPointsMap.get("life");
        int disabilityScore = riskPointsMap.get("disability");

        // Then
        assertEquals(0, lifeScore);
        assertEquals(0, disabilityScore);

    }

    @Test
    void givenUserWithDependents_whenBuildRiskProfile_thenIncreaseOneLifeAndDisabilityRiskPoints() {
        // Given
        riskPointsMap.put("life", 0);
        riskPointsMap.put("disability", 0);
        int dependents = 1;

        // When
        RiskProfileService.updateScoreByDependents(dependents, riskPointsMap);
        int lifeScore = riskPointsMap.get("life");
        int disabilityScore = riskPointsMap.get("disability");

        // Then
        assertEquals(1, lifeScore);
        assertEquals(1, disabilityScore);

    }

    @Test
    void givenMortgagedHouse_whenBuildRiskProfile_thenIncreaseOneHomeAndDisabilityRiskPoints() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("disability", 0);
        House house = new House(OwnershipStatus.mortgaged);

        // When
        RiskProfileService.updateScoreByHouse(house, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");

        // Then
        assertEquals(1, homeScore);
        assertEquals(1, disabilityScore);
    }

    @Test
    void givenOwnedHouse_whenBuildRiskProfile_thenDoNothing() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("disability", 0);
        House house = new House(OwnershipStatus.owned);

        // When
        RiskProfileService.updateScoreByHouse(house, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");

        // Then
        assertEquals(0, homeScore);
        assertEquals(0, disabilityScore);
    }

    @Test
    void givenNoHouse_whenBuildRiskProfile_thenDoNothing() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("disability", 0);

        // When
        RiskProfileService.updateScoreByHouse(null, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");

        // Then
        assertEquals(0, homeScore);
        assertEquals(0, disabilityScore);
    }

    @Test
    void givenOver200000Income_whenBuildRiskProfile_thenDecreaseAllRiskPointsByOne() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("life", 0);
        riskPointsMap.put("auto", 0);
        riskPointsMap.put("disability", 0);
        int income = 300000;

        // When
        RiskProfileService.updateScoreByIncome(income, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");
        int lifeScore = riskPointsMap.get("life");
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(-1, homeScore);
        assertEquals(-1, disabilityScore);
        assertEquals(-1, lifeScore);
        assertEquals(-1, autoScore);
    }

    @Test
    void givenUnder200000Income_whenBuildRiskProfile_thenDoNothing() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("life", 0);
        riskPointsMap.put("auto", 0);
        riskPointsMap.put("disability", 0);
        int income = 50000;

        // When
        RiskProfileService.updateScoreByIncome(income, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");
        int lifeScore = riskPointsMap.get("life");
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(0, homeScore);
        assertEquals(0, disabilityScore);
        assertEquals(0, lifeScore);
        assertEquals(0, autoScore);
    }

    @Test
    void givenUserAgeUnder30_whenBuildRiskProfile_thenDecreaseAllRiskPointsByTwo() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("life", 0);
        riskPointsMap.put("auto", 0);
        riskPointsMap.put("disability", 0);
        int age = 26;

        // When
        RiskProfileService.updateScoreByAge(age, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");
        int lifeScore = riskPointsMap.get("life");
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(-2, homeScore);
        assertEquals(-2, disabilityScore);
        assertEquals(-2, lifeScore);
        assertEquals(-2, autoScore);
    }


    @Test
    void givenUserAgeBetween30And40_whenBuildRiskProfile_thenDecreaseAllRiskPointsByOne() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("life", 0);
        riskPointsMap.put("auto", 0);
        riskPointsMap.put("disability", 0);
        int age = 35;

        // When
        RiskProfileService.updateScoreByAge(age, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");
        int lifeScore = riskPointsMap.get("life");
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(-1, homeScore);
        assertEquals(-1, disabilityScore);
        assertEquals(-1, lifeScore);
        assertEquals(-1, autoScore);
    }

    @Test
    void givenUserAgeOver40_whenBuildRiskProfile_thenDoNothing() {
        // Given
        riskPointsMap.put("home", 0);
        riskPointsMap.put("life", 0);
        riskPointsMap.put("auto", 0);
        riskPointsMap.put("disability", 0);
        int age = 65;

        // When
        RiskProfileService.updateScoreByAge(age, riskPointsMap);
        int homeScore = riskPointsMap.get("home");
        int disabilityScore = riskPointsMap.get("disability");
        int lifeScore = riskPointsMap.get("life");
        int autoScore = riskPointsMap.get("auto");

        // Then
        assertEquals(0, homeScore);
        assertEquals(0, disabilityScore);
        assertEquals(0, lifeScore);
        assertEquals(0, autoScore);
    }

    @Test
    void givenScorePoint_whenBuildRiskProfile_thenReturnCorrectInsurancePlan() {
        // Given
        int score3 = 3;
        int score1 = 1;
        int scoreMinus2 = -2;

        // When
        InsurancePlan responsiblePlan = RiskProfileService.calculateInsurancePlan(score3);
        InsurancePlan regularPlan = RiskProfileService.calculateInsurancePlan(score1);
        InsurancePlan economicPlan = RiskProfileService.calculateInsurancePlan(scoreMinus2);

        // Then
        assertEquals(responsible, responsiblePlan);
        assertEquals(regular, regularPlan);
        assertEquals(economic, economicPlan);
    }

    @Test
    void givenUserRiskAnswers_whenBuildRiskProfile_thenSetCorrectBaseScore() {
        // Given
        List<Integer> ans0 = List.of(0, 0, 0);
        List<Integer> ans1 = List.of(1, 0, 0);
        List<Integer> ans2 = List.of(1, 0, 1);
        List<Integer> ans3 = List.of(1, 1, 1);

        // When
        int baseScore0 = RiskProfileService.calculateBaseScore(ans0);
        int baseScore1 = RiskProfileService.calculateBaseScore(ans1);
        int baseScore2 = RiskProfileService.calculateBaseScore(ans2);
        int baseScore3 = RiskProfileService.calculateBaseScore(ans3);

        // Then
        assertEquals(0, baseScore0);
        assertEquals(1, baseScore1);
        assertEquals(2, baseScore2);
        assertEquals(3, baseScore3);
    }


    @Test
    void givenUserWithoutCarAndHouse_whenBuildRiskProfile_thenSetAutoAndHomeInsuranceIneligible() {
        // Given
        User user = new User(20, 0, null, 50000,
                MaritalStatus.single, List.of(1, 1, 1), null);

        RiskProfile riskProfile = new RiskProfile();

        // When
        RiskProfileService.setIneligibleInsurance(user, riskProfile);

        // Then
        assertEquals(ineligible, riskProfile.getAuto());
        assertEquals(ineligible, riskProfile.getHome());
    }

    @Test
    void givenUserOver60Years_whenBuildRiskProfile_thenSetDisabilityAndLifeInsuranceIneligible() {
        // Given
        User user = new User(66, 3, new House(OwnershipStatus.owned), 50000,
                MaritalStatus.married, List.of(0, 1, 1), new Vehicle(2023));

        RiskProfile riskProfile = new RiskProfile();

        // When
        RiskProfileService.setIneligibleInsurance(user, riskProfile);

        // Then
        assertEquals(ineligible, riskProfile.getDisability());
        assertEquals(ineligible, riskProfile.getLife());
    }

}