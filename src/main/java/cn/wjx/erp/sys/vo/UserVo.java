package cn.wjx.erp.sys.vo;


import cn.wjx.erp.sys.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {
    private Integer page;
    private Integer limit;

}
