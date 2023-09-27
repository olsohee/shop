package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.shop.dto.order.OrderListResponse;
import project.shop.dto.order.OrderRequest;
import project.shop.dto.order.OrderResponse;
import project.shop.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * [POST] /order
     * 주문하기
     */
    @PostMapping("/order")
    public OrderResponse order(HttpServletRequest request, @RequestBody OrderRequest dto) {

        return orderService.order(request, dto);
    }

    /**
     * [GET] /orders
     * 주문 목록 조회
     */
    @GetMapping("/orders")
    public OrderListResponse findOrderList(HttpServletRequest request) {

        return orderService.findOrderList(request);
    }

    /**
     * [GET] /orders/{orderId}
     * 주문 상세 조회
     */
    @GetMapping("/orders/{orderId}")
    public OrderResponse findOrder(HttpServletRequest request,
                                   @PathVariable Long orderId) {

        return orderService.findOrder(request, orderId);
    }

    /**
     * [POST] /orders/{orderId}/cancel
     * 주문 취소
     */

}
