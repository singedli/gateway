package com.ocft.gateway.tree;

import lombok.Data;

import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/11 16:51
 * @Description:
 */
@Data
public class Child {
    private List<Child> child;
    private String file;
    private String resKnowledgeMenu;
    private String pointName;
    private int menuId;
    private int seq;
    private int id;

}
