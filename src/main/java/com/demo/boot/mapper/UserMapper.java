package com.demo.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.boot.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 *
 * @author gnl
 * @date 2021-02-20 21:03
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
