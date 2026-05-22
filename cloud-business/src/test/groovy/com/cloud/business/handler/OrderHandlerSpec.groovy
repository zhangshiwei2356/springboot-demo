package com.cloud.business.handler

import com.cloud.business.dto.SubmitOrderDTO
import com.cloud.business.integration.SystemProductClient
import com.cloud.business.integration.SystemUserClient
import com.cloud.business.integration.dto.ProductRemoteVO
import com.cloud.business.integration.dto.UserRemoteVO
import com.cloud.common.domain.Result
import com.cloud.common.enums.ResultCode
import spock.lang.Specification

class OrderHandlerSpec extends Specification {

    def "编排查询用户与商品"() {
        given:
        def u = new UserRemoteVO()
        u.userId = 1L
        u.userName = "张三"

        def p = new ProductRemoteVO()
        p.productCode = "SKU-DEMO"
        p.unitPrice = new BigDecimal("12.34")

        def userResult = new Result<UserRemoteVO>()
        userResult.setCode(ResultCode.SUCCESS.getCode())
        userResult.setMessage(ResultCode.SUCCESS.getMessage())
        userResult.setData(u)

        def prodResult = new Result<ProductRemoteVO>()
        prodResult.setCode(ResultCode.SUCCESS.getCode())
        prodResult.setMessage(ResultCode.SUCCESS.getMessage())
        prodResult.setData(p)

        SystemUserClient userClient = Stub() {
            getUser(1L) >> userResult
        }
        SystemProductClient productClient = Stub() {
            getPrice("SKU-DEMO") >> prodResult
        }

        def handler = new OrderHandler(userClient, productClient)

        def dto = new SubmitOrderDTO()
        dto.buyerUserId = 1L
        dto.productCode = "SKU-DEMO"
        dto.quantity = 2

        when:
        def out = handler.execute(dto)

        then:
        out.user.userId == 1L
        out.product.unitPrice == new BigDecimal("12.34")
    }
}
