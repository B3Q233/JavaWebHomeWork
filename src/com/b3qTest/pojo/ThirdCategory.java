package com.b3qTest.pojo;

/**
 * @author b3q
 * 三级栏目的 JAVA Bean
 */
public class ThirdCategory {
    private int id;

    public ThirdCategory() {
    }

    private String name;

    private String parentName;

    private int parentId;

    private int categoryOrder;

    private String dir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParenName() {
        return parentName;
    }

    public void setParenName(String parenName) {
        this.parentName = parenName;
    }


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }
}
