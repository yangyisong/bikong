package com.ydhl.outsourcing.ts.finance.base.report;

import com.ydhl.outsourcing.ts.finance.common.report.ExcelColumnHeader;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;

import java.util.ArrayList;

/**
 * @author Martins
 * @create 2018/1/15 15:47.
 * @description
 */
public class OperationReportVo extends ExcelDataDto {

    /**
     * 导出文件头
     */
    public static final ArrayList<ExcelColumnHeader> F_S_COL_HD;

    static {
        F_S_COL_HD = new ArrayList<>();
        F_S_COL_HD.add(new ExcelColumnHeader("日志编号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("发生时间", 30));
        F_S_COL_HD.add(new ExcelColumnHeader("操作人", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("客户端ip", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("操作内容", 40));
        F_S_COL_HD.add(new ExcelColumnHeader("备注", 20));
    }
}
