package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.PatternUtils;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/15 11:20.
 * @description
 */
public class OperationQueryDto extends OperationDto {

    private static final long serialVersionUID = -7987345862256668258L;

    private Date startTime;

    private Date endTime;

    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
