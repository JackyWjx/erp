package cn.wjx.erp.sys.service;

import cn.wjx.erp.sys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-23
 */
public interface IUserService extends IService<User> {

    void saveUserRole(Integer uid, Integer[] ids);
}
