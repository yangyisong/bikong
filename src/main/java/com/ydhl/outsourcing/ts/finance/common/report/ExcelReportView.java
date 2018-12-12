package com.ydhl.outsourcing.ts.finance.common.report;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Map;

public class ExcelReportView extends AbstractXlsxStreamingView {

    @Override
    protected SXSSFWorkbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        ExcelDataDto dto = (ExcelDataDto) model.get(ExcelDataDto.MODEL_NAME);
        return dto.getWorkbook();
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExcelDataDto dto = (ExcelDataDto) model.get(ExcelDataDto.MODEL_NAME);
        String filename = new String(dto.getFileName().getBytes("utf-8"), "ISO8859-1");
        response.addHeader("Content-Disposition", MessageFormat.format("attachment;filename={0}.xlsx", filename));
    }

}
