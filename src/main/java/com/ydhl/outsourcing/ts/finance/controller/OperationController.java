package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.report.OperationReportVo;
import com.ydhl.outsourcing.ts.finance.base.report.ReportConstant;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.query.OperationQueryDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;

/**
 * @author Martins
 * @create 2018/1/10 18:02.
 */
@Controller
@RequestMapping("/operation")
public class OperationController extends BaseController {

    @Autowired
    private OperationService operationService;

    /**
     * 操作日志分页查询
     *
     * @param operationQueryDto 查询条件
     * @param pageModel 分页
     * @return 分页数据
     */
    @GetMapping(value = "/operationList")
    public ModelAndView findOperationPage(OperationQueryDto operationQueryDto, PageModel pageModel) throws UnknownHostException, BusinessException {
        ModelAndView mav = new ModelAndView();
        PageInfo<OperationDto> pageInfo = operationService.findOperationPage(operationQueryDto, pageModel);
        pageInfo.setPageNum(pageModel.getPage());
        mav.addObject("queryDto", operationQueryDto);
        mav.addObject("pageInfo", pageInfo);
        mav.setViewName("operation/operationList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看操作日志列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 操作日志导出
     *
     * @param operationQueryDto 查询条件
     * @return
     */
    @GetMapping(value = "/export.html")
    public ModelAndView export(OperationQueryDto operationQueryDto) throws UnknownHostException, BusinessException {
        PageInfo<OperationDto> dtoPageInfo = operationService.findOperationPage(operationQueryDto);
        OperationReportVo operationReportVo = new OperationReportVo();
        int sheetNum = operationReportVo.addOneSheet(OperationReportVo.F_S_COL_HD);
        for (OperationDto operationDto : dtoPageInfo.getList()) {
            operationReportVo.fillOneRow(
                    sheetNum
                    , operationDto.getId()
                    , operationDto.getOperateTime()
                    , operationDto.getOperatorName()
                    , operationDto.getOperateIp()
                    , operationDto.getContent()
                    , operationDto.getRemark()
            );
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("导出操作日志列表");
        operationService.insertOperation(operationDto);
        operationReportVo.setFileName(ReportConstant.OPERATION_REPORT);
        return new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, operationReportVo);
    }

}
