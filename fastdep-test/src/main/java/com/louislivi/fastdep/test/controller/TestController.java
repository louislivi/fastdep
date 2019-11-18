package com.louislivi.fastdep.test.controller;

import com.louislivi.fastdep.test.dao.Test;
import com.louislivi.fastdep.test.dao.UserRequestData;
import com.louislivi.fastdep.test.mapper.test.TestMapper;
import com.louislivi.fastdep.test.mapper.douyin.UserRequestDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : louislivi
 */
@RestController
public class TestController {

    @Autowired
    private UserRequestDataMapper userRequestDataMapper;

    @Autowired
    private TestMapper testMapper;

    @Transactional
    @GetMapping("test")
    public void test() {
        List<UserRequestData> userRequestData = userRequestDataMapper.selectAll();
        System.out.println(userRequestData);
        List<Test> tests = testMapper.selectAll();
        System.out.println(tests);
//        UserRequestData userRequestData1 = new UserRequestData();
//        userRequestData1.setUserId(11L);
//        userRequestDataMapper.insert(userRequestData1);
//        int i = 1 / 0;
//        testMapper.insert();
    }
}
