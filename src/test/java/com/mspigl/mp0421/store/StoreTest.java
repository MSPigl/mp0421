package com.mspigl.mp0421.store;

import com.mspigl.mp0421.catalog.Catalog;
import com.mspigl.mp0421.catalog.CatalogItem;
import com.mspigl.mp0421.tool.Tool;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Store
 */
class StoreTest {

    @Test
    void should_throwException_when_toolNotInCatalog() {
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
    void should_throwException_when_rentalDayCountInvalid() {
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
    void should_throwException_when_discountPercentageInvalid() {
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
    void should_throwException_when_checkoutDateInvalid() {
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
}