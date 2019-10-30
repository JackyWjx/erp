package cn.wjx.erp.sys.service.impl;

import cn.wjx.erp.sys.domain.User;
import cn.wjx.erp.sys.mapper.RoleMapper;
import cn.wjx.erp.sys.mapper.UserMapper;
import cn.wjx.erp.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-23
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户id删除用户角色中间表中的数据
        roleMapper.deleteRoleUserByUid(id);
        //删除用户头[如果是默认头像不删除  否则删除]
        return super.removeById(id);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //根据用户id删除sys_role_user表中的数据
        this.roleMapper.deleteRoleUserByUid(uid);
        if(null!=ids&&ids.length>0){
            for (Integer id:ids) {
                this.roleMapper.saveUserRole(uid,id);
            }
        }

    }
}
