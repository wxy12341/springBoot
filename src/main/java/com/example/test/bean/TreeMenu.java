package com.example.test.bean;

//import com.baomidou.mybatisplus.annotation.TableField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tree_menu")
public class TreeMenu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int menuid;


    private String name;


    private Integer parentMenuId;


    private Integer meunLevel;


    private Integer sort;

    /**
     * 用于保存一个菜单的子菜单
     */
//    @TableField(exist = false)
//    private List<TreeMenu> treeMenu;

    @Column(name="menu_id")
    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    @Column(nullable = false,unique = true,name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="parent_menu_id")
    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    @Column(name="meun_level")
    public Integer getMeunLevel() {
        return meunLevel;
    }

    public void setMeunLevel(Integer meunLevel) {
        this.meunLevel = meunLevel;
    }

    @Column(name="sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

//    public List<TreeMenu> getTreeMenu() {
//        return treeMenu;
//    }
//
//    public void setTreeMenu(List<TreeMenu> treeMenu) {
//        this.treeMenu = treeMenu;
//    }
}
