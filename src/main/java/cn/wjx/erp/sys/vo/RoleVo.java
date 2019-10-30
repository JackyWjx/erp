package cn.wjx.erp.sys.vo;

import cn.wjx.erp.sys.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {
    private Integer page=1;
    private Integer limit=10;
    private Integer[] ids;
}
