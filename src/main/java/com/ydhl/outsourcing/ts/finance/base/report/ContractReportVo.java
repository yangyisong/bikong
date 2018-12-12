package com.ydhl.outsourcing.ts.finance.base.report;

import com.ydhl.outsourcing.ts.finance.common.report.ExcelColumnHeader;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;

import java.util.ArrayList;

/**
 * @author Junpeng.Su
 * @create 2017-12-19 下午 2:57
 * @description
 */
public class ContractReportVo extends ExcelDataDto {

    /**
     * 导出文件头
     */
    public static final ArrayList<ExcelColumnHeader> F_S_COL_HD;

    static {
        F_S_COL_HD = new ArrayList<>();
        F_S_COL_HD.add(new ExcelColumnHeader("合同编号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("姓名", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("合同金额", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("产品名称", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("生效日期", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("到期日期", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("户名", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("开户银行", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("账号", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("签约类型", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("业务人员", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("状态", 20));
        F_S_COL_HD.add(new ExcelColumnHeader("备注", 30));
    }

}
