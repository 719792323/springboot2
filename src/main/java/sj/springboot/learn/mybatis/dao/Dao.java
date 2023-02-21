package sj.springboot.learn.mybatis.dao;

import org.apache.ibatis.annotations.Select;
import sj.springboot.learn.mybatis.entity.Entity;

import java.util.List;

//@Mapper
public interface Dao {
    Entity getEntityById(Integer id);

    @Select("select * from payment")
    List<Entity> getAllEntities();
}
