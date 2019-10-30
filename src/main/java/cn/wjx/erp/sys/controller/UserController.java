package cn.wjx.erp.sys.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PinyinUtil;
import cn.wjx.erp.sys.common.Constast;
import cn.wjx.erp.sys.common.DataGridView;
import cn.wjx.erp.sys.common.ResultObj;
import cn.wjx.erp.sys.domain.Dept;
import cn.wjx.erp.sys.domain.Role;
import cn.wjx.erp.sys.domain.User;
import cn.wjx.erp.sys.service.IDeptService;
import cn.wjx.erp.sys.service.IRoleService;
import cn.wjx.erp.sys.service.IUserService;
import cn.wjx.erp.sys.vo.DeptVo;
import cn.wjx.erp.sys.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRoleService roleService;

    /**
     * 用户全查询
     * @return
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()),"loginname",userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//查询系统用户
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        userService.page(page,queryWrapper);

        List<User> list = page.getRecords();
        for (User user :
                list) {
            Integer deptid = user.getDeptid();
            if(deptid!=null){
                Dept one = deptService.getById(deptid);
                user.setDeptname(one.getTitle());
            }
            Integer mgr = user.getMgr();
            if(mgr!=null){
                User one = this.userService.getById(mgr);
                user.setLeadername(one.getName());
            }
        }
        return new DataGridView(page.getTotal(),list);
    }


    /**
     * 根据部门id查询部门
     * @param deptid
     * @return
     */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
        List<User> list = this.userService.list(queryWrapper);

        return new DataGridView(list);
    }

    /**
     * 初始化排序码
     * @param deptVo
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Integer> loadUserMaxOrderNum(DeptVo deptVo){
        List<User> userVos = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        try{
            userVos = this.userService.list();
            if(userVos.size()>0){
                map.put("value",userVos.size()+1);
            }else {
                map.put("value",1);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return  map;
    }
    /**
     * 根据部门id查询部门
     * @param
     * @return
     */
    @RequestMapping("changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){
        String pinyin = PinyinUtil.getPinYin(username);
        Map<String,Object> map = new HashMap<>();
        if(username!=null&&!"".equals(username)){
            map.put("value",pinyin);
            System.out.println(pinyin);
        }else {
            map.put("value","");
        }

        return map;
    }

    /**
     * 添加
     * @param userVo
     * @return
     */
    @RequestMapping("addUser")
    public ResultObj addUser(UserVo userVo){

        try{
            userVo.setType(Constast.USER_TYPE_NORMAL);//设置用户类型
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toLowerCase();
            userVo.setSalt(salt);//设置盐
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());//设置密码

            this.userService.save(userVo);
            return ResultObj.ADD_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo userVo){
       try{
           this.userService.updateById(userVo);
           return ResultObj.UPDATE_SUCCESS;
       }catch (Exception e){
           e.printStackTrace();
           return ResultObj.UPDATE_ERROR;
       }

    }


    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try{
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    @RequestMapping("resetPwd")
    public ResultObj resetPwd(Integer id){
        try{
            User user0 = new User();
            UserVo u = (UserVo) user0;
            UserVo user = new UserVo();
            User i = (UserVo)user;
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            this.userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }

    }

    /**
     * 根据用户ID查询角色并选中已拥有的角色
     * @return
     */
    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        //1.查询所有的可用角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> maps = this.roleService.listMaps(queryWrapper);
        //2.查询所选用户的可用角色
        List<Integer> ids = this.roleService.queryUserRoleIdsByUid(id);
        for (Map<String,Object> map:maps) {
            Boolean LAY_CHECKED=false;
            Integer roleId = (Integer) map.get("id");
            for (Integer i :ids) {
                if (i == roleId) {
                    LAY_CHECKED = true;
                }
            map.put("LAY_CHECKED",LAY_CHECKED);
            }
        }
        return new DataGridView((long)maps.size(),maps);
    }

    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer [] ids){
        try {
            this.userService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }

    }

}

