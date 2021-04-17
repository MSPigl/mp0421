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
}