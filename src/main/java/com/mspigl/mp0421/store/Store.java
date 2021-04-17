package com.mspigl.mp0421.store;

import com.mspigl.mp0421.catalog.Catalog;

/**
 * Class representing a store that tools can be rented from
 */
public class Store {

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
        return null;
    }
}
