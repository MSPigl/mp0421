package com.mspigl.mp0421.store;

import com.mspigl.mp0421.catalog.Catalog;
import com.mspigl.mp0421.catalog.CatalogItem;
import com.mspigl.mp0421.tool.Tool;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Store
 */
class StoreTest {

    /**
     * Store instance to use for spec acceptance tests
     */
    private static final Store SPEC_STORE;

    static {
        List<Tool> tools = Arrays.asList(
                new Tool("LADW", "Werner", "Ladder"),
                new Tool("CHNS", "Stihl", "Chainsaw"),
                new Tool("JAKR", "Ridgid", "Jackhammer"),
                new Tool("JAKD", "DeWalt", "Jackhammer")
        );

        List<CatalogItem> items = Arrays.asList(
                new CatalogItem("Ladder", 1.99, true, true, false),
                new CatalogItem("Chainsaw", 1.49, true, false, true),
                new CatalogItem("Jackhammer", 2.99, true, false, false)
        );

        SPEC_STORE = new Store(new Catalog(tools, items));
    }

    /* SANITY TESTS */

    @Test
    void should_throwException_when_checkoutWithToolNotInCatalog() {
        List<Tool> tools = Arrays.asList(
                new Tool("Code1", "Brand1", "Type1"),
                new Tool("Code2", "Brand2", "Type2")
        );

        List<CatalogItem> items = Arrays.asList(
                new CatalogItem("Type1", 1.99, true, true, true),
                new CatalogItem("Type3", 3.99, true, true, true)
        );

        Catalog catalog = new Catalog(tools, items);
        Store store = new Store(catalog);

        try {
            store.checkout("Code3", 3, 4, "10/11/21");
        } catch (IllegalArgumentException e) {
            assertEquals(Store.TOOL_NOT_IN_CATALOG_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    void should_throwException_when_checkoutWithRentalDayCountInvalid() {
        List<Tool> tools = Arrays.asList(
                new Tool("Code1", "Brand1", "Type1"),
                new Tool("Code2", "Brand2", "Type2")
        );

        List<CatalogItem> items = Arrays.asList(
                new CatalogItem("Type1", 1.99, true, true, true),
                new CatalogItem("Type2", 2.99, true, true, true)
        );

        Catalog catalog = new Catalog(tools, items);
        Store store = new Store(catalog);

        try {
            store.checkout("Code2", -1, 4, "10/11/21");
        } catch (IllegalArgumentException e) {
            assertEquals(Store.INVALID_RENTAL_DAY_COUNT_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    void should_throwException_when_checkoutWithDiscountPercentageInvalid() {
        List<Tool> tools = Arrays.asList(
                new Tool("Code1", "Brand1", "Type1"),
                new Tool("Code2", "Brand2", "Type2")
        );

        List<CatalogItem> items = Arrays.asList(
                new CatalogItem("Type1", 1.99, true, true, true),
                new CatalogItem("Type2", 2.99, true, true, true)
        );

        Catalog catalog = new Catalog(tools, items);
        Store store = new Store(catalog);

        try {
            store.checkout("Code2", 3, 120, "10/11/21");
        } catch (IllegalArgumentException e) {
            assertEquals(Store.INVALID_DISCOUNT_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    void should_throwException_when_checkoutWithCheckoutDateInvalid() {
        List<Tool> tools = Arrays.asList(
                new Tool("Code1", "Brand1", "Type1"),
                new Tool("Code2", "Brand2", "Type2")
        );

        List<CatalogItem> items = Arrays.asList(
                new CatalogItem("Type1", 1.99, true, true, true),
                new CatalogItem("Type2", 2.99, true, true, true)
        );

        Catalog catalog = new Catalog(tools, items);
        Store store = new Store(catalog);

        try {
            store.checkout("Code2", 4, 4, "23/45/21");
        } catch (IllegalArgumentException e) {
            assertEquals(Store.INVALID_CHECKOUT_DATE_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    void should_beChargeable_when_dayIsWeekdayAndWeekdayChargeable() {
        LocalDate april16 = LocalDate.of(2021, Month.APRIL, 16);
        CatalogItem catalogItem = new CatalogItem("type", 0, true, true, true);

        assertTrue(Store.isChargeableDay(april16, catalogItem));
    }

    @Test
    void should_NotBeChargeable_when_dayIsWeekdayAndWeekdayNotChargeable() {
        LocalDate april16 = LocalDate.of(2021, Month.APRIL, 16);
        CatalogItem catalogItem = new CatalogItem("type", 0, false, true, true);

        assertFalse(Store.isChargeableDay(april16, catalogItem));
    }

    @Test
    void should_beChargeable_when_dayIsWeekendAndWeekendChargeable() {
        LocalDate april17 = LocalDate.of(2021, Month.APRIL, 17);
        CatalogItem catalogItem = new CatalogItem("type", 0, true, true, true);

        assertTrue(Store.isChargeableDay(april17, catalogItem));
    }

    @Test
    void should_notBeChargeable_when_dayIsWeekendAndWeekendNotChargeable() {
        LocalDate april17 = LocalDate.of(2021, Month.APRIL, 17);
        CatalogItem catalogItem = new CatalogItem("type", 0, true, false, true);

        assertFalse(Store.isChargeableDay(april17, catalogItem));
    }

    @Test
    void should_beHoliday_when_dayIsJuly3AndJuly4IsSaturday() {
        LocalDate july3 = LocalDate.of(2020, Month.JULY, 3);

        assertTrue(Store.isHoliday(july3));
    }

    @Test
    void should_beHoliday_when_dayIsJuly5AndJuly4IsSunday() {
        LocalDate july5 = LocalDate.of(2021, Month.JULY, 5);

        assertTrue(Store.isHoliday(july5));
    }

    @Test
    void should_beHoliday_when_dayIsJuly4AndNotAWeekend() {
        LocalDate july4 = LocalDate.of(2022, Month.JULY, 4);

        assertTrue(Store.isHoliday(july4));
    }

    @Test
    void should_beHoliday_when_dayIsSeptember6Of2021() {
        LocalDate september6 = LocalDate.of(2021, Month.SEPTEMBER, 6);

        assertTrue(Store.isHoliday(september6));
    }

    @Test
    void should_beChargeable_when_dayIsHolidayAndHolidayChargeable() {
        LocalDate july4 = LocalDate.of(2022, Month.JULY, 4);
        CatalogItem catalogItem = new CatalogItem("type", 0, true, true, true);

        assertTrue(Store.isChargeableDay(july4, catalogItem));
    }

    @Test
    void should_notBeChargeable_when_dayIsHolidayAndHolidayNotChargeable() {
        LocalDate july4 = LocalDate.of(2022, Month.JULY, 4);
        CatalogItem catalogItem = new CatalogItem("type", 0, true, true, false);

        assertFalse(Store.isChargeableDay(july4, catalogItem));
    }

    /* SPEC TESTS */

    /**
     * The passed in discount (101) is outside of the valid range, so an exception is thrown
     */
    @Test
    void spec_1_should_throwException() {
        try {
            SPEC_STORE.checkout("JAKR", 5, 101, "9/3/15");
        } catch (IllegalArgumentException e) {
            assertEquals(Store.INVALID_DISCOUNT_ERROR_MESSAGE, e.getMessage());
        }
    }

    /**
     * The rental days are 7/3/20, 7/4/20 and 7/5/20.
     * 7/3/20 is Independence Day observed because the 4th is a Saturday,
     * and ladders are not charged on holidays, so 7/3/20 doesn't add to the total.
     * 7/4/20 is a weekend (not a holiday) since Independence Day was observed the day
     * before, ladders are charged on weekends so 7/4/20 adds to the total.
     * 7/5/20 is a weekend, ladders are charged on weekends so 7/5/20 adds to the total.
     *
     * 2 chargeable days * 1.99 per day = 3.98 before discount.
     * 3.98 * .1 = .40 discount
     * 3.98 - .4 = 3.58 final charge
     */
    @Test
    void spec_2_should_generateRentalAgreement() {
        RentalAgreement rentalAgreement = SPEC_STORE.checkout("LADW", 3, 10, "7/2/20");

        assertEquals(3.98, rentalAgreement.getPreDiscountCharge());
        assertEquals(.40, rentalAgreement.getDiscountAmount());
        assertEquals(3.58, rentalAgreement.getFinalCharge());
        assertEquals("07/05/20", rentalAgreement.getDueDate());
    }

    /**
     * The rental days are 7/3/15 - 7/7/15 (inclusive).
     * 7/3/20 is Independence Day observed because the 4th is a Saturday,
     * and chainsaws are charged on holidays, so 7/3/20 adds to the total.
     * 7/4/20 is a weekend (not a holiday) since Independence Day was observed the day
     * before, chainsaws are not charged on weekends so 7/4/20 doesn't add to the total.
     * 7/5/20 is a weekend, chainsaws are not charged on weekends so 7/5/20 doesn't add to the total.
     * 7/6/20 and 7/7/20 are weekdays, chainsaws are charged on weekdays so these days add to the total.
     *
     * 3 chargeable days * 1.49 per day = 4.47 before discount.
     * 4.47 * .25 = 1.12 discount
     * 4.47 - 1.12 = 3.35 final charge
     */
    @Test
    void spec_3_should_generateRentalAgreement() {
        RentalAgreement rentalAgreement = SPEC_STORE.checkout("CHNS", 5, 25, "7/2/15");

        assertEquals(4.47, rentalAgreement.getPreDiscountCharge());
        assertEquals(1.12, rentalAgreement.getDiscountAmount());
        assertEquals(3.35, rentalAgreement.getFinalCharge());
        assertEquals("07/07/15", rentalAgreement.getDueDate());
    }

    /**
     * The rental days are 9/4/15 - 9/9/15 (inclusive)
     * 9/4/15 is a weekday, jackhammers are charged on weekdays so 9/4/15 adds to the total.
     * 9/5/15 and 9/6/15 are weekends, jackhammers are not charged on weekends so these don't add to the total.
     * 9/7/15 is Labor Day, jackhammers are not charged on holidays so 9/7/15 doesn't add to the total.
     * 9/8/15 and 9/9/15 are weekdays, jackhammers are charged on weekdays so these add to the total.
     *
     * 3 chargeable days * 2.99 per day = 8.97 before discount.
     * No discount is applied for this rental so the final total is 8.97.
     */
    @Test
    void spec_4_should_generateRentalAgreement() {
        RentalAgreement rentalAgreement = SPEC_STORE.checkout("JAKD", 6, 0, "9/3/15");

        assertEquals(8.97, rentalAgreement.getPreDiscountCharge());
        assertEquals(0, rentalAgreement.getDiscountAmount());
        assertEquals(8.97, rentalAgreement.getFinalCharge());
        assertEquals("09/09/15", rentalAgreement.getDueDate());
    }

    /**
     * The rental days are 7/3/15 - 7/11/15 (inclusive)
     * 7/3/15 is Independence Day observed because the 4th is a Saturday,
     * and jackhammers are not charged on holidays, so 7/3/20 doesn't add to the total.
     * 7/4/15 and 7/5/15 are weekends, jackhammers are not charged on weekends, so these don't add to the total.
     * 7/6/15 - 7/10/15 are weekdays, jackhammers charge on weekdays, so these add to the total.
     * 7/11/15 is a weekend, jackhammers are not charged on weekends, so this doesn't add to the total.
     *
     * 5 chargeable days * 2.99 per day = 14.95 before discount.
     * No discount is applied for this rental so the final total is 14.95
     */
    @Test
    void spec_5_should_generateRentalAgreement() {
        RentalAgreement rentalAgreement = SPEC_STORE.checkout("JAKR", 9, 0, "7/2/15");

        assertEquals(14.95, rentalAgreement.getPreDiscountCharge());
        assertEquals(0, rentalAgreement.getDiscountAmount());
        assertEquals(14.95, rentalAgreement.getFinalCharge());
        assertEquals("07/11/15", rentalAgreement.getDueDate());
    }
}