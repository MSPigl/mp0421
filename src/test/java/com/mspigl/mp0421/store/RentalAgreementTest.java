package com.mspigl.mp0421.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test RentalAgreement calculations
 */
class RentalAgreementTest {

    RentalAgreement rentalAgreement;

    @BeforeEach
    void setUp() {
        rentalAgreement = new RentalAgreement();
        rentalAgreement.setChargeDays(4);
        rentalAgreement.setDailyCharge(4.99);
        rentalAgreement.setDiscountPercent(20);
    }

    @Test
    void getPreDiscountCharge() {
        double preDiscountCharge = rentalAgreement.getPreDiscountCharge();

        assertEquals(19.96, preDiscountCharge);
    }

    @Test
    void getDiscountAmount() {
        double discountAmount = rentalAgreement.getDiscountAmount();

        assertEquals(3.99, discountAmount);
    }

    @Test
    void getFinalCharge() {
        double finalCharge = rentalAgreement.getFinalCharge();

        assertEquals(15.97, finalCharge);
    }
}