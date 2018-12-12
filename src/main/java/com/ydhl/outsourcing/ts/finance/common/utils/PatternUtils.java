package com.ydhl.outsourcing.ts.finance.common.utils;

/**
 * @author Martins
 * @create 2018/1/12 16:59.
 */
public class PatternUtils {

    /**
     * 会员等级名字验证
     */
    public static final String LEVEL_NAME = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";

    /**
     * 手机格式验证
     */
    public static final String FORMAT_PHONE = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$";

    /**
     * 手机或座机格式校验
     */
    public static final String FORMAT_PHONE_OR_TEL = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}|(0[0-9]{2,3}-)?([1-9][0-9]{6,7})$";

    /**
     * 密码格式验证
     */
    public static final String FORMAT_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    /**
     * 邮箱格式验证
     */
    public static final String FORMAT_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 年月日
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 时分秒
     */
    public static final String FORMAT_TIME = "HH:mm:ss";

    /**
     * 年月日 时分秒
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd";

    /**
     * 身份证号码验证
     */
    public static final String FORMAT_IDCARD = "[1-9]{6}19[0-9]{2}((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))[0-9]{3}[0-9x]{1}";


    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
