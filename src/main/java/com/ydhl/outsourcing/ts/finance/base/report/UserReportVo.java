package com.ydhl.outsourcing.ts.finance.base.report;

import com.ydhl.outsourcing.ts.finance.common.report.ExcelColumnHeader;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;

import java.util.ArrayList;

/**
 * @author Junpeng.Su
 * @create 2017-12-19 下午 2:57
 * @description
 */
public class UserReportVo extends ExcelDataDto {

    /**
     * 导出文件头
     */
    public static final ArrayList<ExcelColumnHeader> F_S_COL_HD;

    static {
        F_S_COL_HD = new ArrayList<>();
        F_S_COL_HD.add(new ExcelColumnHeader("员工编号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("员工姓名", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("手机号", 30));
        F_S_COL_HD.add(new ExcelColumnHeader("职位", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("所属部门", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("客户数量", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("客户数量", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("上次登录时间", 30));
        F_S_COL_HD.add(new ExcelColumnHeader("创建时间", 30));
    }

}
