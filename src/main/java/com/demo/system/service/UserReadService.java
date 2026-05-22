package com.demo.system.service;

import com.demo.system.dto.UserQueryDTO;
import com.demo.system.vo.UserVO;

/**
 * 用户读服务接口。
 */
public interface UserReadService {

    UserVO getByUserId(Long userId);
}
