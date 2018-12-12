package com.ydhl.outsourcing.ts.finance.base.report;

import com.ydhl.outsourcing.ts.finance.common.report.ExcelColumnHeader;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;

import java.util.ArrayList;

/**
 * @author Martins
 * @create 2018/1/21 18:31.
 * @description
 */
public class CustomerReportVo extends ExcelDataDto {

    /**
     * 文件头
     */
    public static final ArrayList<ExcelColumnHeader> F_S_COL_HD;

    static {
        F_S_COL_HD = new ArrayList<>();
        F_S_COL_HD.add(new ExcelColumnHeader("客户姓名", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("客户性别", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("身份证号", 30));
        F_S_COL_HD.add(new ExcelColumnHeader("手机号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("客户年龄", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("出生年月", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("座机", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("微信号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("客户邮箱", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("QQ号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("合同数", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("紧急联系人", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("紧急联系人电话", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("家庭住址", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("客户类型", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("所属业务员", 10));
        F_S_COL_HD.add(new ExcelColumnHeader("添加时间", 40));
    }
}
