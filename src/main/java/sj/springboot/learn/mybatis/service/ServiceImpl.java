package sj.springboot.learn.mybatis.service;

import sj.springboot.learn.mybatis.entity.Entity;
import sj.springboot.learn.mybatis.dao.Dao;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Resource
    private Dao dao;

    @Override
    public Entity getEntityById(Integer id) {
        return dao.getEntityById(id);
    }

    @Override
    public List<Entity> getAllEntities() {
        return dao.getAllEntities();
    }
}
