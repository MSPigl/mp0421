package com.mspigl.mp0421.catalog;

/**
 * Class representing a tool type matched with rental charging information
 */
public class CatalogItem {

    private String toolType;
    private double dailyCharge;
    private boolean weekdayChargeable;
    private boolean weekendChargeable;
    private boolean holidayChargeable;

    /**
     * Construct a CatalogItem instance
     * @param toolType the item's tool type
     * @param dailyCharge the item's daily charge
     * @param weekdayChargeable whether the tool accrues charges on weekdays
     * @param weekendChargeable whether the tool accrues charges on weekends
     * @param holidayChargeable whether the tool accrues charges on holidays
     */
    public CatalogItem(
            String toolType,
            double dailyCharge,
            boolean weekdayChargeable,
            boolean weekendChargeable,
            boolean holidayChargeable
    ) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayChargeable = weekdayChargeable;
        this.weekendChargeable = weekendChargeable;
        this.holidayChargeable = holidayChargeable;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean getWeekdayChargeable() {
        return weekdayChargeable;
    }

    public void setWeekdayChargeable(boolean weekdayChargeable) {
        this.weekdayChargeable = weekdayChargeable;
    }

    public boolean getWeekendChargeable() {
        return weekendChargeable;
    }

    public void setWeekendChargeable(boolean weekendChargeable) {
        this.weekendChargeable = weekendChargeable;
    }

    public boolean getHolidayChargeable() {
        return holidayChargeable;
    }

    public void setHolidayChargeable(boolean holidayChargeable) {
        this.holidayChargeable = holidayChargeable;
    }
}
