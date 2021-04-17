package com.mspigl.mp0421.store;

import com.mspigl.mp0421.catalog.Catalog;
import com.mspigl.mp0421.catalog.CatalogItem;
import com.mspigl.mp0421.tool.Tool;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

        RentalAgreement rentalAgreement = new RentalAgreement();

        return rentalAgreement;
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
