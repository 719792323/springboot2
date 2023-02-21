#!/bin/bash
docker run -d \
--name sj_redis \
-p 6379:6379 \
-v /home/redis/data:/data \
-v /home/redis/conf/redis.conf:/etc/redis/redis.conf \
redis redis-server /etc/redis/redis.conf



