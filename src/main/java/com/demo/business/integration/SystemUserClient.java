package com.demo.business.integration;

import com.demo.business.integration.dto.UserRemoteVO;
import com.demo.common.domain.Result;

/**
 * 系统用户查询（单机模式下为进程内调用）。
 */
public interface SystemUserClient {

    Result<UserRemoteVO> getUser(Long userId);
}
