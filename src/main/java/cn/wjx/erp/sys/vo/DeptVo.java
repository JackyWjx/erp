package cn.wjx.erp.sys.vo;

import cn.wjx.erp.sys.domain.Dept;
import cn.wjx.erp.sys.domain.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {
    private static final long serialVersionUID = 1L;

    private Integer page=1;

    private Integer limit=10;

}
