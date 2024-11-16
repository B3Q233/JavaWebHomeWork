package com.b3qTest.pojo;

/**
 * @author b3q
 * 三级栏目与详情页关系  JAVA Bean
 */
public class CategoryDetailPage {
    private int categoryId;

    private int detailPageId;

    public CategoryDetailPage() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDetailPageId() {
        return detailPageId;
    }

    public void setDetailPageId(int detailPageId) {
        this.detailPageId = detailPageId;
    }



}
