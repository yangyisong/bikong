package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.UserLoginService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2017/5/22 下午3:19.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登陆页面
     * @return
     */
/*    @RequestMapping(value = "/login.html",method = RequestMethod.GET)
    public String login() {
        return "login";
    }*/

    /**
     * 登陆
     * @return
     */
   @RequestMapping(value = "/loginIn.do",method = RequestMethod.GET)
    public ModelAndView loginIn(String username, String password) throws BusinessException {
        ModelAndView mav = getModelAndView();
        UserDto userDto = userService.userLogin(username, password);
        if(userDto != null) {
            getSession().setAttribute("user",userDto);
        }else {
            mav.addObject("errorMessage","用户名或密码错误");
            mav.setViewName("login");
            return mav;
        }
        mav.setViewName("redirect:/index/index.html");
        return mav;
    }


    /**
     * 退出登陆
     * @return
     */
    @RequestMapping(value = "/loginOut.do",method = RequestMethod.POST)
    public ModelAndView loginOut() {
        ModelAndView mav = getModelAndView();
        getSession().removeAttribute("user");
        mav.setViewName("redirect:login");
        return mav;
    }
}
