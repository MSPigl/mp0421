package com.mspigl.mp0421.store;

import java.text.NumberFormat;
import java.util.Locale;

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

    /**
     * Print stringified rental agreement to console
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Output all fields and calculated amounts as a string
     * @return all fields/amounts as a string
     */
    @Override
    public String toString() {
        return "Tool code: " +
                toolCode +
                "\n" +
                "Tool type: " +
                toolType +
                "\n" +
                "Tool brand: " +
                toolBrand +
                "\n" +
                "Rental days: " +
                rentalDays +
                "\n" +
                "Checkout date: " +
                checkoutDate +
                "\n" +
                "Due date: " +
                dueDate +
                "\n" +
                "Daily rental charge: " +
                formatCurrency(dailyCharge) +
                "\n" +
                "Charge days: " +
                chargeDays +
                "\n" +
                "Pre-discount charge: " +
                formatCurrency(getPreDiscountCharge()) +
                "\n" +
                "Discount percent: " +
                discountPercent +
                "%\n" +
                "Discount amount: " +
                formatCurrency(getDiscountAmount()) +
                "\n" +
                "Final charge: " +
                formatCurrency(getFinalCharge());
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
     * and the discount amount rounded half up to cents)
     * @return the final charge
     */
    public double getFinalCharge() {
        double preDiscountCharge = getPreDiscountCharge();
        double discountAmount = getDiscountAmount();

        return Math.round((preDiscountCharge - discountAmount) * 100) / 100d;
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

    /**
     * Format the input amount into USD
     * @param amount the amount to format
     * @return a currency string representing the formatted amount
     */
    private String formatCurrency(double amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        return numberFormat.format(amount);
    }
}
