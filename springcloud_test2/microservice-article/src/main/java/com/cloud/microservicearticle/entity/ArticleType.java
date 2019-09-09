package com.cloud.microservicearticle.entity;

public class ArticleType {
    private Integer id;
    private Integer typeId;
    private String typeName;
    private String typeTitle;

    public ArticleType() {
    }

    public ArticleType(Integer id, Integer typeId, String typeName, String typeTitle) {
        this.id = id;
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeTitle = typeTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }
}
