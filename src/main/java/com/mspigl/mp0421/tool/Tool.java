package com.mspigl.mp0421.tool;

/**
 * Class representing a tool
 */
public class Tool {

    private String code;
    private String brand;
    private String type;

    /**
     * Construct a Tool instance
     * @param code the tool's unique identifier
     * @param brand the tool's brand
     * @param type the tool's type
     */
    public Tool(String code, String brand, String type) {
        this.code = code;
        this.brand = brand;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
