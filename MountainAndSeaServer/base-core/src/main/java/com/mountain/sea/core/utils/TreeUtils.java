package com.mountain.sea.core.utils;

import com.mountain.sea.core.vo.TreeNodeVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 14:14
 */
public class TreeUtils<T extends TreeNodeVO> {

    public TreeUtils() {
    }

    public static <T extends TreeNodeVO> List<T> bulid(List<T> treeNodes, Object root, Comparator comparator) {
        List<T> trees = new ArrayList();
        Iterator var4 = treeNodes.iterator();

        while(var4.hasNext()) {
            T treeNode = (T)var4.next();
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            Iterator var6 = treeNodes.iterator();

            while(var6.hasNext()) {
                T it = (T)var6.next();
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList());
                    }

                    treeNode.add(it);
                }
            }

            if (comparator != null) {
                List children = treeNode.getChildren();
                treeNode.getChildren().sort(comparator);
            }
        }

        return trees;
    }

    public static <T extends TreeNodeVO> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList();
        Iterator var3 = treeNodes.iterator();

        while(var3.hasNext()) {
            T treeNode = (T)var3.next();
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }

        return trees;
    }

    public static <T extends TreeNodeVO> T findChildren(T treeNode, List<T> treeNodes) {
        Iterator var2 = treeNodes.iterator();

        while(var2.hasNext()) {
            T it = (T)var2.next();
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList());
                }

                treeNode.add(findChildren(it, treeNodes));
            }
        }

        return treeNode;
    }
}
