package com.mspigl.mp0421.catalog;

import com.mspigl.mp0421.tool.Tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a catalog of tools with rental prices by tool type
 */
public class Catalog {

    private final Map<String, CatalogItem> items;
    private final Map<String, Tool> tools;

    /**
     * Construct a Catalog instance
     */
    public Catalog() {
        this.items = new HashMap<>();
        this.tools = new HashMap<>();
    }

    /**
     * Construct a Catalog instance
     * @param tools the list of tools available in this catalog (must not be null)
     * @param items the list of catalog items available in this catalog (must not be null)
     */
    public Catalog(List<Tool> tools, List<CatalogItem> items) {
        if (tools == null || items == null) {
            throw new IllegalArgumentException(
                    "Cannot instantiate a catalog without tools or catalog items"
            );
        }

        this.tools = new HashMap<>();
        this.items = new HashMap<>();

        for (Tool tool : tools) {
            this.tools.put(tool.getCode(), tool);
        }

        for (CatalogItem item : items) {
            this.items.put(item.getToolType(), item);
        }
    }

    /**
     * Get the catalog item for the input tool type
     * @param toolType the requested tool type
     * @return the catalog item matching the input tool type, null if not found
     */
    public CatalogItem getCatalogItem(String toolType) {
        return items.get(toolType);
    }

    /**
     * Get the tool for the input tool code
     * @param toolCode the requested tool code
     * @return the tool matching the input tool code, null if not found
     */
    public Tool getTool(String toolCode) {
        return tools.get(toolCode);
    }
}
