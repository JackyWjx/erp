package cn.wjx.erp.sys.service.impl;

import cn.wjx.erp.sys.domain.Dept;
import cn.wjx.erp.sys.mapper.DeptMapper;
import cn.wjx.erp.sys.service.IDeptService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王吉祥
 * @since 2019-10-27
 */
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public boolean save(Dept entity) {
        return super.save(entity);
    }

    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }


    @Override
    public boolean updateById(Dept entity) {
        return super.updateById(entity);
    }

}
