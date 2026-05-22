package com.cloud.business.integration;

import com.cloud.business.integration.dto.UserRemoteVO;
import com.cloud.common.domain.Result;

/**
 * 系统用户查询（单机模式下为进程内调用）。
 */
public interface SystemUserClient {

    Result<UserRemoteVO> getUser(Long userId);
}
