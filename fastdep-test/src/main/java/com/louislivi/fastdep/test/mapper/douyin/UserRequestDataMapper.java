package com.louislivi.fastdep.test.mapper.douyin;

import com.louislivi.fastdep.test.dao.UserRequestData;

import java.util.List;

public interface UserRequestDataMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(UserRequestData record);

    UserRequestData selectByPrimaryKey(Long userId);

    List<UserRequestData> selectAll();

    List<UserRequestData> selectOptions();

    int updateByPrimaryKey(UserRequestData record);
}