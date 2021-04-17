package com.mspigl.mp0421.catalog;

import com.mspigl.mp0421.tool.Tool;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Catalog
 */
class CatalogTest {

    @Test
    void should_buildToolAndItemMap() {
        List<Tool> tools = Arrays.asList(
                new Tool("Tool1", "Brand1", "Type1"),
                new Tool("Tool2", "Brand2", "Type2")
        );

        List<CatalogItem> items = Arrays.asList(
                new CatalogItem("Type2", 2.99, true, true, true),
                new CatalogItem("Type1", 1.99, true, true, true)
        );

        Catalog catalog = new Catalog(tools, items);

        Tool tool1 = catalog.getTool("Tool1");
        Tool tool2 = catalog.getTool("Tool2");
        CatalogItem item1 = catalog.getCatalogItem("Type1");
        CatalogItem item2 = catalog.getCatalogItem("Type2");

        assertEquals("Type1", tool1.getType());
        assertEquals("Type2", tool2.getType());
        assertEquals(1.99, item1.getDailyCharge());
        assertEquals(2.99, item2.getDailyCharge());
    }

    @Test
    void should_ThrowException_when_ToolListIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Catalog(null, null);
        });
    }
}