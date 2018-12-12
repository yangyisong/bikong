package com.ydhl.outsourcing.ts.finance.common.utils;

import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;

/**
 * @author Martins
 * @create 2018/1/29 16:51.
 * @description
 */
public class OperaionUtil {

    public static OperationDto getOperationDto() throws BusinessException, UnknownHostException {
        OperationDto operationDto = new OperationDto();
        operationDto.setOperatorName(CurrentUserUtils.currentUserRealname());
        operationDto.setOperateTime(DateHelperPlus.getNow().getTime());
        operationDto.setOperateIp(CurrentUserUtils.getLocalhostIp());
        return operationDto;
    }
}
