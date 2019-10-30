package cn.wjx.erp.sys.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {
    public static List<TreeNode> build(List<TreeNode> lists , Integer topPid){
        List<TreeNode> nodes = new ArrayList<>();
        for (TreeNode t1 :lists){
            if(t1.getPid()==topPid){
                nodes.add(t1);
            }
            for (TreeNode t2:lists) {
                if(t2.getPid()==t1.getId()){
                    t1.getChildren().add(t2);
                }
            }
        }
        return nodes;
    }
}
