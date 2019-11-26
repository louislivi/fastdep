package com.louislivi.fastdep.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.louislivi.fastdep.test.dao.Test;
import com.louislivi.fastdep.test.dao.UserRequestData;
import com.louislivi.fastdep.test.mapper.douyin.UserRequestDataMapper;
import com.louislivi.fastdep.test.mapper.test.TestMapper;
import com.louislivi.fastdep.test.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TestController
 *
 * @author : louislivi
 */
@RestController
public class TestController {

    @Autowired
    private UserRequestDataMapper userRequestDataMapper;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private StringRedisTemplate redis1StringRedisTemplate;

    @Autowired
    private StringRedisTemplate redis2StringRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * datasource test
     * @return
     */
    //    @Transactional
    @GetMapping("test")
    public String test() {
        PageHelper.startPage(1, 1);
        List<UserRequestData> userRequestData = userRequestDataMapper.selectAll();
        PageInfo<UserRequestData> userRequestDataPageInfo = new PageInfo<>(userRequestData);
        System.out.println(userRequestDataPageInfo);
        PageHelper.startPage(1, 2);
        List<Test> tests = testMapper.selectAll();
        PageInfo<Test> testPageInfo = new PageInfo<>(tests);
        System.out.println(testPageInfo);
        return JSONObject.toJSONString(testPageInfo);
//        UserRequestData userRequestData1 = new UserRequestData();
//        userRequestData1.setUserId(11L);
//        userRequestDataMapper.insert(userRequestData1);
//        int i = 1 / 0;
//        testMapper.insert();
    }

    /**
     * redis test
     */
    @GetMapping("redis")
    public void redis() {
        System.out.println(redis1StringRedisTemplate.opsForValue().get("test"));
        System.out.println(redis2StringRedisTemplate.opsForValue().get("test"));
        System.out.println(redisUtil.redisTemplate("redis1").opsForValue().get("test"));
        System.out.println(redisUtil.stringRedisTemplate("redis2").opsForValue().get("test"));
    }
}
