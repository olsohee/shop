package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.order.OrderRequest;
import project.shop.dto.order.OrderResponse;
import project.shop.entity.Order;
import project.shop.entity.OrderProduct;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.jwt.JwtTokenUtils;
import project.shop.service.OrderService;
import project.shop.service.ProductService;
import project.shop.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    /**
     * [POST] /order
     * 주문하기
     */
    @PostMapping("/order")
    public OrderResponse checkout(HttpServletRequest request, @RequestBody OrderRequest dto) {

        // 저장
        User user = findUserFromRequest(request);
        Long orderId = orderService.order(user, dto.getOrderProducts());

        // 조회
        Order findOrder = orderService.findById(orderId);

        return OrderResponse.createResponse(findOrder, findOrder.getOrderProducts());
    }



    // request를 통해 사용자 조회
    private User findUserFromRequest(HttpServletRequest request) {

        String email = jwtTokenUtils.getEmailFromHeader(request);
        return userService.findByEmail(email);
    }
}
