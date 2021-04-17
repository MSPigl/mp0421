package com.mspigl.mp0421.store;

import com.mspigl.mp0421.catalog.Catalog;
import com.mspigl.mp0421.catalog.CatalogItem;
import com.mspigl.mp0421.tool.Tool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a store that tools can be rented from
 */
public class Store {

    static final String TOOL_NOT_IN_CATALOG_ERROR_MESSAGE = "The tool requested at checkout is not present in our catalog.";
    static final String INVALID_RENTAL_DAY_COUNT_ERROR_MESSAGE = "A tool must be rented for one or more days.";
    static final String INVALID_DISCOUNT_ERROR_MESSAGE = "Applied discount must be in the range 0 - 100 (inclusive)";
    static final String INVALID_CHECKOUT_DATE_ERROR_MESSAGE = "The checkout date must be a valid date in a valid format";

    private final Catalog catalog;

    /**
     * Construct a Store instance
     * @param catalog the catalog to assign to the store instance
     */
    public Store(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Generate a rental agreement based on the store catalog and
     * the input data
     * @param toolCode the code of the tool being rented
     * @param rentalDayCount the number of days the tool will be rented (must be greater than 0)
     * @param discountPercent the discount percent to apply to the total charge
     * @param checkoutDate the checkout date (mm/dd/yy/)
     * @return a RentalAgreement containing the calculated rental totals
     */
    public RentalAgreement checkout(
            String toolCode,
            int rentalDayCount,
            int discountPercent,
            String checkoutDate
    ) {
        this.validateCheckoutInput(toolCode, rentalDayCount, discountPercent, checkoutDate);

        Tool toolToRent = catalog.getTool(toolCode);
        CatalogItem toolToRentCatalogItem = catalog.getCatalogItem(toolToRent.getType());
        LocalDate localDate = LocalDate.parse(checkoutDate);
        int chargeDays = 0;

        for (int i = 0; i < rentalDayCount; i++) {
            localDate = localDate.plusDays(1);

            if (isChargeDay(localDate, toolToRentCatalogItem)) {
                chargeDays++;
            }
        }

        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setRentalDays(rentalDayCount);
        rentalAgreement.setDiscountPercent(discountPercent);
        rentalAgreement.setCheckoutDate(checkoutDate);
        rentalAgreement.setToolCode(toolToRent.getCode());
        rentalAgreement.setToolType(toolToRent.getType());
        rentalAgreement.setToolBrand(toolToRent.getBrand());
        rentalAgreement.setDailyCharge(toolToRentCatalogItem.getDailyCharge());
        rentalAgreement.setChargeDays(chargeDays);
        rentalAgreement.setDueDate(
                localDate.format(DateTimeFormatter.ofPattern("M/d/y")) // TODO: finalize format
        );

        return rentalAgreement;
    }

    /**
     * Determine if the input day is chargeable
     * @param day the day to test if chargeable
     * @param catalogItem the catalog item to compare against the day
     * @return whether the input day is chargeable
     */
    boolean isChargeDay(LocalDate day, CatalogItem catalogItem) {
        boolean isChargeDay;

        DayOfWeek dayOfWeek = day.getDayOfWeek();
        boolean isWeekend = dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);

        if (isHoliday(day)) {
            isChargeDay = catalogItem.getHolidayChargeable();
        } else if (isWeekend) {
            isChargeDay = catalogItem.getWeekendChargeable();
        } else {
            isChargeDay = catalogItem.getWeekdayChargeable();
        }

        return isChargeDay;
    }

    /**
     * Determine if the input day is a holiday. Valid holidays are Independence Day (July 4th,
     * observed July 3rd if 4th is a Saturday, July 5th if Sunday) and Labor Day (first Monday
     * of September)
     * @param day the day to test
     * @return whether the input day is a holiday
     */
    boolean isHoliday(LocalDate day) {
        boolean isHoliday;

        Month month = day.getMonth();

        if (month.equals(Month.JULY)) {
            LocalDate independenceDay = LocalDate.of(day.getYear(), Month.JULY, 4);
            LocalDate independenceDayObserved;

            if (independenceDay.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                independenceDayObserved = independenceDay.minusDays(1);
            } else if (independenceDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                independenceDayObserved = independenceDay.plusDays(1);
            } else {
                independenceDayObserved = independenceDay;
            }

            isHoliday = day.getDayOfMonth() == independenceDayObserved.getDayOfMonth();
        } else if (month.equals(Month.SEPTEMBER)) {
            LocalDate laborDay = day.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

            isHoliday = day.getDayOfMonth() == laborDay.getDayOfMonth();
        } else {
            isHoliday = false;
        }

        return isHoliday;
    }

    /**
     * Validate that the the input tool code has a corresponding entry in the store catalog,
     * the rental day count is greater than zero, the discount percent is in the range [0, 100]
     * (inclusive) and that the checkout date is valid
     * @param toolCode the code of the tool being rented
     * @param rentalDayCount the number of days the tool will be rented
     * @param discountPercent the discount percent to apply to the total charge
     * @param checkoutDate the checkout date (mm/dd/yy/)
     */
    private void validateCheckoutInput(
            String toolCode,
            int rentalDayCount,
            int discountPercent,
            String checkoutDate
    ) {
        Tool toolToRent = catalog.getTool(toolCode);
        CatalogItem toolToRentCatalogItem = toolToRent == null ? null : catalog.getCatalogItem(toolToRent.getType());

        if (toolToRentCatalogItem == null) {
            throw new IllegalArgumentException(TOOL_NOT_IN_CATALOG_ERROR_MESSAGE);
        }

        if (rentalDayCount < 1) {
            throw new IllegalArgumentException(INVALID_RENTAL_DAY_COUNT_ERROR_MESSAGE);
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_ERROR_MESSAGE);
        }

        validateCheckoutDate(checkoutDate);
    }

    /**
     * Validate that the input date string is both in valid format (mm/dd/yy, leading zero
     * optional for month and day) and that it is a valid date
     * @param checkoutDate the date to validate
     */
    private void validateCheckoutDate(String checkoutDate) {
        Pattern datePattern = Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d\\d$");
        Matcher dateMatcher = datePattern.matcher(checkoutDate);
        boolean dateValid = dateMatcher.matches();

        if (dateValid) {
            try {
                LocalDate.parse(checkoutDate);
            } catch (DateTimeParseException e) {
                dateValid = false;
            }
        }

        if (!dateValid) {
            throw new IllegalArgumentException(INVALID_CHECKOUT_DATE_ERROR_MESSAGE);
        }
    }
}
