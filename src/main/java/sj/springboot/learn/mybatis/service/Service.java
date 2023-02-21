package sj.springboot.learn.mybatis.service;

import sj.springboot.learn.mybatis.entity.Entity;

import java.util.List;

public interface Service {

    Entity getEntityById(Integer id);

    List<Entity> getAllEntities();
}
