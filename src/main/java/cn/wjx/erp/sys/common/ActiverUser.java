package cn.wjx.erp.sys.common;

import cn.wjx.erp.sys.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor//全参构造器
@NoArgsConstructor//无参构造器
public class ActiverUser {
    private User user;

    private List<String> roles;

    private List<String> permission;
}
