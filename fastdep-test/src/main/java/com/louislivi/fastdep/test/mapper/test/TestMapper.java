package com.louislivi.fastdep.test.mapper.test;


import com.louislivi.fastdep.test.dao.Test;

import java.util.List;

/**
 * @author : louislivi
 * @date : 2019-11-13 15:55
 */

public interface TestMapper {

    List<Test> selectAll();

    Integer insert();
}
