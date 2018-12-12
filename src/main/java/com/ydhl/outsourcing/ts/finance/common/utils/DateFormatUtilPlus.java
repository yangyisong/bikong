/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ydhl.outsourcing.ts.finance.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;


public final class DateFormatUtilPlus extends DateFormatUtils {
    public static final String SYSTEM_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SYSTEM_DATE_MIN_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String SYSTEM_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormatUtilPlus() {

    }

    /**
     * 将日期格式化成指定格式，如果日期为空，则返回空字符串
     *
     * @param date    待格式化日期
     * @param pattern 格式化表达式
     * @return 格式化后的日期
     */
    public static String formatIfNull(final Date date, final String pattern) {
        if (date == null) {
            return StringUtilPlus.EMPTY;
        }
        return format(date, pattern, null, null);
    }

    /**
     * 将日期格式化成中文年月日方式
     *
     * @param date 待格式化日期
     * @return 中文日期
     */
    public static String formatCnIfNull(final Date date) {
        if (date == null) {
            return StringUtilPlus.EMPTY;
        }
        String[] dateS = StringUtilPlus.split(DateFormatUtils.format(date, "yyyy-MM-dd", null, null), StringUtilPlus.HYPHEN);
        StringBuilder sb = new StringBuilder();
        sb.append(NumberFormatUtilPlus.formatCnYear(dateS[0]));
        sb.append("\u5e74");
        sb.append(NumberFormatUtilPlus.formatCnNum(dateS[1]));
        sb.append("\u6708");
        sb.append(NumberFormatUtilPlus.formatCnNum(dateS[2]));
        sb.append("\u65e5");
        return sb.toString();
    }

    /**
     * 将日期格式化成中文月日时分方式
     *
     * @param date 待格式化日期
     * @return xx月xx日 xx时xx分
     */
    public static String formatCnTimeIfNull(final Date date) {
        if (date == null) {
            return StringUtilPlus.EMPTY;
        }
        String[] dateS = StringUtilPlus.split(DateFormatUtils.format(date, "yyyy-MM-dd-HH-mm-ss", null, null), StringUtilPlus.HYPHEN);
        StringBuilder sb = new StringBuilder();
        sb.append(dateS[1]);
        sb.append("\u6708");
        sb.append(dateS[2]);
        sb.append("\u65e5 ");
        sb.append(dateS[3]);
        sb.append("\u65f6");
        sb.append(dateS[4]);
        sb.append("\u5206");
        return sb.toString();
    }
}
