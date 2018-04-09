package com.ecgobike.common.constant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/9.
 */
public class MonthNumFee {
    public static final int ONE = 1;
    public static final int TEN = 10;
    public static final int FIFTEEN = 15;
    public static final int TWENTY = 20;
    public static final int TWENTY_FIVE = 25;
    public static final int THIRTY = 30;
    public static final int THIRTY_FIVE = 35;
    public static final int FORTY = 40;
    public static final int FIFTY = 50;

    static Map<Integer, BigDecimal> rule = new HashMap<>();

    public static Map<Integer, BigDecimal> getRule() {
        if (rule.size() == 0) {
            initRule();
        }
        return rule;
    }

    public static BigDecimal getFee(int num) {
        if (rule.size() == 0) {
            initRule();
        }
        return rule.get(num);
    }

    static void initRule() {
        rule.put(ONE,  new BigDecimal(0.8));
        rule.put(TEN, new BigDecimal(7));
        rule.put(FIFTEEN, new BigDecimal(9));
        rule.put(TWENTY, new BigDecimal(11));
        rule.put(TWENTY_FIVE, new BigDecimal(13));
        rule.put(THIRTY, new BigDecimal(15));
        rule.put(THIRTY_FIVE, new BigDecimal(17));
        rule.put(FORTY, new BigDecimal(19));
        rule.put(FIFTY, new BigDecimal(23));
    }
}
