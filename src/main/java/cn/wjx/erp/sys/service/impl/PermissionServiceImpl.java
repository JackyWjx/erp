package cn.wjx.erp.sys.service.impl;

import cn.wjx.erp.sys.domain.Permission;
import cn.wjx.erp.sys.mapper.PermissionMapper;
import cn.wjx.erp.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-24
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public boolean removeById(Serializable id) {
        PermissionMapper baseMapper = this.getBaseMapper();
        //根据权限或菜单id删除角色表和权限表里面的数据
        baseMapper.deleteRolePermissionById(id);
        return super.removeById(id);
    }
}
