package com.example.test.mapper;

import com.example.test.bean.TreeMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface MenuDao {
    @Select("SELECT menu_id as menuId,name as name," +
            "parent_menu_id as parentMenuId,meun_level as meunLevel,sort as sort FROM tree_menu ")
    List<TreeMenu> getTreeMenu();
}
