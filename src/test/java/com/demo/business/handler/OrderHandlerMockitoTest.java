package com.demo.business.handler;

import com.demo.business.dto.SubmitOrderDTO;
import com.demo.business.integration.SystemProductClient;
import com.demo.business.integration.SystemUserClient;
import com.demo.business.integration.dto.ProductRemoteVO;
import com.demo.business.integration.dto.UserRemoteVO;
import com.demo.business.vo.OrderIntegrationResult;
import com.demo.common.domain.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** JUnit + Mockito：与 Groovy Spock 双栈示例共存。 */
@ExtendWith(MockitoExtension.class)
class OrderHandlerMockitoTest {

    @Mock
    private SystemUserClient userClient;

    @Mock
    private SystemProductClient productClient;

    @InjectMocks
    private OrderHandler orderHandler;

    @Test
    void executeDelegatesToBothClients() {
        SubmitOrderDTO dto = new SubmitOrderDTO();
        dto.setBuyerUserId(5L);
        dto.setProductCode("SKU-DEMO");
        dto.setQuantity(1);

        UserRemoteVO user = new UserRemoteVO();
        user.setUserId(5L);
        when(userClient.getUser(5L)).thenReturn(Result.ok(user));

        ProductRemoteVO prod = new ProductRemoteVO();
        prod.setProductCode("SKU-DEMO");
        prod.setUnitPrice(BigDecimal.TEN);
        when(productClient.getPrice("SKU-DEMO")).thenReturn(Result.ok(prod));

        OrderIntegrationResult res = orderHandler.execute(dto);

        Assertions.assertEquals(BigDecimal.TEN, res.getProduct().getUnitPrice());
        verify(userClient).getUser(5L);
        verify(productClient).getPrice("SKU-DEMO");
    }
}
