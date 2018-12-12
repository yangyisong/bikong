package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.dao.ApprovedApplicationDao;
import com.ydhl.outsourcing.ts.finance.dao.ContractApplyDao;
import com.ydhl.outsourcing.ts.finance.dao.PersonalIncomeDao;
import com.ydhl.outsourcing.ts.finance.dto.InfoDto;
import com.ydhl.outsourcing.ts.finance.dto.LoginSecurityDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by huangxinguang on 2017/5/17.
 */
@Controller
public class IndexController {

    @Autowired
    private ContractApplyDao applyDao;

    @Autowired
    private ApprovedApplicationDao applicationDao;

    @Autowired
    private PersonalIncomeDao incomeDao;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/index.html",method = RequestMethod.GET)
    public String index() {
        return "index/index";
    }

    /*@RequestMapping(value = "/main.html",method = RequestMethod.GET)
    public String main() {
        return "index/main";
    }*/

    @RequestMapping(value = "/main.html",method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView mv = new ModelAndView();
        InfoDto infoDto = new InfoDto();
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        Integer sApprovIng = 0;
        Integer qApprovIng= 0;
        Integer cApprovIng= 0;
        Integer rApprovIng= 0;
        Integer jApprovIng= 0;
        Integer sApprovAre= 0;
        Integer jApprovAre= 0;
        Integer sApprovW= 0;
        Integer qApprovW= 0;
        Integer cApprovW= 0;
        Integer rApprovW= 0;
        Integer jApprovW= 0;
        Integer iPayW= 0;
        if (roleId == 2 || roleId == 3) {
            sApprovIng = applyDao.getCountApprovByTypeAndUserId(userId, ContractType.ADV, ApplicationStruts.BREV);
            qApprovIng = applyDao.getCountApprovByTypeAndUserId(userId, ContractType.QUI, ApplicationStruts.BREV);
            cApprovIng = applyDao.getCountApprovByTypeAndUserId(userId, ContractType.CON, ApplicationStruts.BREV);
            rApprovIng = applyDao.getCountApprovByTypeAndUserId(userId, ContractType.REN, ApplicationStruts.BREV);
            // 结算申请中
            jApprovIng = applicationDao.getCountApprovByUserId(userId, ApplicationStruts.BREV);
            //签约被驳回
            sApprovAre = applyDao.getCountArejByTypeAndUserId(userId, ApplicationStruts.AREJ);
            //结算驳回
            jApprovAre = applicationDao.getCountApprovByUserId(userId, ApplicationStruts.AREJ);
        }
        if (roleId == 3) {
            sApprovW = applyDao.getCountWaitApprovByUserId(0, ContractType.ADV);
            qApprovW = applyDao.getCountWaitApprovByUserId(0, ContractType.QUI);
            cApprovW = applyDao.getCountWaitApprovByUserId(0, ContractType.CON);
            rApprovW = applyDao.getCountWaitApprovByUserId(0, ContractType.REN);
        }else if (roleId == 4) {
            sApprovW = applyDao.getCountWaitApprovByUserId(1, ContractType.ADV);
            qApprovW = applyDao.getCountWaitApprovByUserId(1, ContractType.QUI);
            cApprovW = applyDao.getCountWaitApprovByUserId(1, ContractType.CON);
            rApprovW = applyDao.getCountWaitApprovByUserId(1, ContractType.REN);
            //收益待付款
            iPayW = incomeDao.getCountPayWaitIncome();
        }else if (roleId == 5) {
            sApprovW = applyDao.getCountWaitApprovByUserId(2, ContractType.ADV);
            qApprovW = applyDao.getCountWaitApprovByUserId(2, ContractType.QUI);
            cApprovW = applyDao.getCountWaitApprovByUserId(2, ContractType.CON);
            rApprovW = applyDao.getCountWaitApprovByUserId(2, ContractType.REN);
        }
        if (roleId > 2) {
            jApprovW = applicationDao.getCountApprovByUserId(roleId - 3, ApplicationStruts.BREV);
        }
        infoDto.setsApprovIng(sApprovIng);
        infoDto.setsApprovW(sApprovW);
        infoDto.setsApprovAre(sApprovAre);
        infoDto.setqApprovIng(qApprovIng);
        infoDto.setqApprovW(qApprovW);
        infoDto.setcApprovIng(cApprovIng);
        infoDto.setcApprovW(cApprovW);
        infoDto.setrApprovIng(rApprovIng);
        infoDto.setrApprovW(rApprovW);
        infoDto.setiPayW(iPayW);
        infoDto.setjApprovIng(jApprovIng);
        infoDto.setjApprovW(jApprovW);
        infoDto.setjApprovAre(jApprovAre);
        mv.addObject("infoDto",infoDto);
        mv.setViewName("index/main");
        return mv;
    }

    @RequestMapping(value = "/403.html",method = RequestMethod.GET)
    public String page403() {
        return "index/403";
    }

    @RequestMapping(value = "/menu.html",method = RequestMethod.POST)
    @ResponseBody
    public Long menu() {
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        return roleId;
    }
}
