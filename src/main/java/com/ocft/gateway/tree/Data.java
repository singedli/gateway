package com.ocft.gateway.tree;

import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/11 16:50
 * @Description:
 */
@lombok.Data
public class Data {
    private List<Child> child;
    private String file;
    private String resKnowledgeMenu;
    private String pointName;
    private int menuId;
    private int seq;
    private int id;

}
