package cn.wjx.erp.sys.mapper;

import cn.wjx.erp.sys.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-24
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    void deleteRolePermissionById(@Param("id") Serializable id);
}
