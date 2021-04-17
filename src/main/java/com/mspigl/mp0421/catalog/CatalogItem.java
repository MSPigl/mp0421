package com.mspigl.mp0421.catalog;

/**
 * Class representing a tool type matched with rental charging information
 */
public class CatalogItem {

    private String toolType;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    /**
     * Construct a CatalogItem instance
     * @param toolType the item's tool type
     * @param dailyCharge the item's daily charge
     * @param weekdayCharge whether the tool accrues charges on weekdays
     * @param weekendCharge whether the tool accrues charges on weekends
     * @param holidayCharge whether the tool accrues charges on holidays
     */
    public CatalogItem(
            String toolType,
            double dailyCharge,
            boolean weekdayCharge,
            boolean weekendCharge,
            boolean holidayCharge
    ) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
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

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }
}
