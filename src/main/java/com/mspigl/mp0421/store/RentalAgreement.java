package com.mspigl.mp0421.store;

/**
 * Class representing a rental agreement for a tool
 */
public class RentalAgreement {

    private String toolCode;
    private String toolType;
    private String toolBrand;
    private String checkoutDate;
    private String dueDate;
    private int rentalDays;
    private int chargeDays;
    private int discountPercent;
    private double dailyCharge;

    @Override
    public String toString() {
        // TODO: Implement
        return super.toString();
    }

    /**
     * Get the pre-discount charge (the product of the number of charge days
     * and the daily charge rate rounded half up to cents)
     * @return the pre-discount charge
     */
    public double getPreDiscountCharge() {
        return Math.round((this.chargeDays * this.dailyCharge) * 100) / 100d;
    }

    /**
     * Get the discount amount (the product of the discount percent and
     * the pre-discount charge rounded half up to cents)
     * @return the discount amount
     */
    public double getDiscountAmount() {
        double preDiscountCharge = getPreDiscountCharge();
        double discountPercent = this.discountPercent / 100d;

        return Math.round((discountPercent * preDiscountCharge) * 100) / 100d;
    }

    /**
     * Get the final charge (the difference of the pre-discount charge
     * and the discount amount)
     * @return the final charge
     */
    public double getFinalCharge() {
        double preDiscountCharge = getPreDiscountCharge();
        double discountAmount = getDiscountAmount();

        return preDiscountCharge - discountAmount;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }
}
