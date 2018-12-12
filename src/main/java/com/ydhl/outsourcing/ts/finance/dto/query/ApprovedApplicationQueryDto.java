package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.common.utils.PatternUtils;
import com.ydhl.outsourcing.ts.finance.dto.ApprovedApplicationDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/16 19:59.
 * @description
 */
public class ApprovedApplicationQueryDto extends ApprovedApplicationDto {

    private static final long serialVersionUID = 6045714255600903897L;

    private Integer searchTab;

    private String content;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 业务员id集合
     */
    private List<Long> userIds;

    private List<Long> approvedIds;

    private Integer approvedStruts;

    private Boolean supportManagerAudit;

    private Integer flag = 0;

    public Date getStartTime() {
        return startTime;
    }

    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Integer getSearchTab() {
        return searchTab;
    }

    public void setSearchTab(Integer searchTab) {
        this.searchTab = searchTab;
        if(searchTab==0){
            setSettleNumber(getContent());
        }else if(searchTab==1){
            setUserRealname(getContent());
        }else if (searchTab == -1){
            this.searchTab = null;
        }else {}
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Integer getApprovedStruts() {
        return approvedStruts;
    }

    @Override
    public void setApprovedStruts(Integer approvedStruts) {
        this.approvedStruts = approvedStruts;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }

    public List<Long> getApprovedIds() {
        return approvedIds;
    }

    public void setApprovedIds(List<Long> approvedIds) {
        this.approvedIds = approvedIds;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
