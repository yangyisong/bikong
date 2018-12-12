package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.dto.DictionaryDto;
import com.ydhl.outsourcing.ts.finance.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/17 18:37.
 * @description
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 获得所有字典数据
     *
     * @return
     */
    @GetMapping(value = "/getAllDictionary")
    public ModelAndView getAllDictionary() {
        ModelAndView mav = new ModelAndView();
        List<DictionaryDto> dictionaryDtos = dictionaryService.getAllDictionary();
        mav.addObject("list", dictionaryDtos);
        return mav;
    }
}
