package com.ydhl.outsourcing.ts.finance.base.report;

import com.ydhl.outsourcing.ts.finance.common.report.ExcelColumnHeader;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;

import java.util.ArrayList;

/**
 * @author Martins
 * @create 2018/1/16 18:16.
 * @description
 */
public class IncomeReportVo extends ExcelDataDto {

    /**
     * 导出文件头
     */
    public static final ArrayList<ExcelColumnHeader> F_S_COL_HD;

    static {
        F_S_COL_HD = new ArrayList<>();
        F_S_COL_HD.add(new ExcelColumnHeader("账单编号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("合同编号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("客户", 15));
        F_S_COL_HD.add(new ExcelColumnHeader("产品", 15));
        F_S_COL_HD.add(new ExcelColumnHeader("业务员", 15));
        F_S_COL_HD.add(new ExcelColumnHeader("结息月份", 30));
        F_S_COL_HD.add(new ExcelColumnHeader("利息", 15));
        F_S_COL_HD.add(new ExcelColumnHeader("计息天数", 15));
        F_S_COL_HD.add(new ExcelColumnHeader("结息类型", 15));
        F_S_COL_HD.add(new ExcelColumnHeader("状态", 15));
    }
}
