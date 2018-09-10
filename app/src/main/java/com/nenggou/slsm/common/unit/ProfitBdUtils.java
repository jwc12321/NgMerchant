package com.nenggou.slsm.common.unit;

import java.math.BigDecimal;

/**
 * Created by JWC on 2018/9/10.
 */

public class ProfitBdUtils {
    public static BigDecimal getProfitBd(String price, String interestRate, String financingCycle) {
        BigDecimal percentageBd = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal allYearBd = new BigDecimal(365).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal interestRateBd = new BigDecimal(interestRate).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal financingCycleBd = new BigDecimal(financingCycle).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal priceDd = new BigDecimal(price).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal profitBd = priceDd.multiply(interestRateBd).divide(percentageBd).multiply(financingCycleBd).divide(allYearBd, 2, BigDecimal.ROUND_DOWN);
        return profitBd;
    }
}
