package com.cloud.business.integration;

import com.cloud.business.integration.dto.UserRemoteVO;
import com.cloud.common.domain.Result;
import com.cloud.system.service.UserReadService;
import com.cloud.system.vo.UserVO;
import org.springframework.stereotype.Component;

@Component
public class SystemUserLocalClient implements SystemUserClient {

    private final UserReadService userReadService;

    public SystemUserLocalClient(UserReadService userReadService) {
        this.userReadService = userReadService;
    }

    @Override
    public Result<UserRemoteVO> getUser(Long userId) {
        UserVO user = userReadService.getByUserId(userId);
        UserRemoteVO remote = new UserRemoteVO(user.getUserId(), user.getUserName());
        return Result.ok(remote);
    }
}
