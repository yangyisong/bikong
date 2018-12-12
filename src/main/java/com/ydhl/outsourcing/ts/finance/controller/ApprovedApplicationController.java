package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.service.ApprovedApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Martins
 * @create 2018/1/16 20:34.
 * @description
 */
@Controller
@RequestMapping("/approvedApplication")
public class ApprovedApplicationController extends BaseController {

    @Autowired
    private ApprovedApplicationService approvedApplicationService;
}
