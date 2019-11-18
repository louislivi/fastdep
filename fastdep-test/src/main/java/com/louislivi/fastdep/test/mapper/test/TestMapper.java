package com.louislivi.fastdep.test.mapper.test;


import com.louislivi.fastdep.test.dao.Test;

import java.util.List;

/**
 * @author : louislivi
 */

public interface TestMapper {

    List<Test> selectAll();

    Integer insert();
}
