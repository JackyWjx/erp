package cn.wjx.erp.sys.controller;


import cn.wjx.erp.sys.common.DataGridView;
import cn.wjx.erp.sys.common.ResultObj;
import cn.wjx.erp.sys.common.TreeNode;
import cn.wjx.erp.sys.domain.Dept;
import cn.wjx.erp.sys.service.IDeptService;
import cn.wjx.erp.sys.vo.DeptVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    /**
     * 加载部门管理左边的部门树的json
     * @param deptVo
     * @return
     */
    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo){
        List<Dept> list = this.deptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();

        for (Dept dept:list) {
            Boolean spread = dept.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }


    /**
     * 查询
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        IPage<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.eq(deptVo.getId()!=null,"id",deptVo.getId()).or().eq(deptVo.getId()!=null,"pid",deptVo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("addDept")
    public ResultObj addDept(DeptVo deptVo){
        try{
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVo deptVo){
        try{
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除前检查是否有子节点
      * @param deptVo
     * @return
     */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Integer> checkDeptHasChildrenNode(DeptVo deptVo){
        Map<String,Integer> map = new HashMap<>();
        List<Dept> lists = new ArrayList<>();
        try{
            lists = this.deptService.list();
            for(Dept dept:lists){
                if(dept.getPid()==deptVo.getId()){
                    map.put("value",1);
                    return map;
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return map;
    }

    /**
     * 删除
     * @param deptVo
     * @return
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try{
            this.deptService.removeById(deptVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Integer> loadDeptMaxOrderNum(DeptVo deptVo){
        List<Dept> deptVos = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        try{
            deptVos = this.deptService.list();
            if(deptVos.size()>0){
                map.put("value",deptVos.size()+1);
            }else {
                map.put("value",1);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return  map;
    }


}

