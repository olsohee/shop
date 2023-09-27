package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.order.OrderListResponse;
import project.shop.dto.order.OrderRequest;
import project.shop.dto.order.OrderResponse;
import project.shop.entity.User;
import project.shop.jwt.JwtTokenUtils;
import project.shop.service.OrderService;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * [POST] /order
     * 주문하기
     */
    @PostMapping("/order")
    public OrderResponse checkout(HttpServletRequest request, @RequestBody OrderRequest dto) {

        return orderService.order(request, dto);
    }

    /**
     * [GET] /orders
     * 주문 목록 조회
     */
    @GetMapping("/orders")
    public OrderListResponse findOrderList(HttpServletRequest request) {

        return orderService.findListByUser(request);
    }

    /**
     * [GET] /orders/{orderId}
     * 주문 상세 조회
     */

    /**
     * [POST] /orders/{orderId}/cancel
     * 주문 취소
     */

}
