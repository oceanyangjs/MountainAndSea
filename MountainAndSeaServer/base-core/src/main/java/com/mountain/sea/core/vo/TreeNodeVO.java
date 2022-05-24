package com.mountain.sea.core.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 14:14
 */
public class TreeNodeVO<T> {
    protected Object id;
    protected Object parentId;
    List<T> children = new ArrayList();

    public TreeNodeVO() {
    }

    public List<T> getChildren() {
        return this.children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getParentId() {
        return this.parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public void add(T node) {
        this.children.add(node);
    }
}
