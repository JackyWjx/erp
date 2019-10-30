package cn.wjx.erp.sys.controller;

import cn.wjx.erp.sys.common.ActiverUser;
import cn.wjx.erp.sys.common.ResultObj;
import cn.wjx.erp.sys.common.WebUtils;
import cn.wjx.erp.sys.domain.Loginfo;
import cn.wjx.erp.sys.service.ILoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.timestamp.TSRequest;

import java.util.Date;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    ILoginfoService loginfoService;

    @RequestMapping("login")
    public ResultObj login(String loginname,String pwd){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(loginname, pwd);
        try {
            subject.login(token);
            ActiverUser user = (ActiverUser) subject.getPrincipal();
            WebUtils.getSession().setAttribute("user",user.getUser());

            //添加登陆日志
            Loginfo entity = new Loginfo();
            entity.setLoginname(user.getUser().getName()+"-"+user.getUser().getLoginname());
            entity.setLoginip(WebUtils.getRequest().getLocalAddr());
            entity.setLogintime(new Date());
            loginfoService.save(entity);
            return ResultObj.LOGIN_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }
}
