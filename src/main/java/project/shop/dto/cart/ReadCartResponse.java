package project.shop.dto.cart;

import lombok.Data;
import project.shop.dto.product.ReadProductResponse;
import project.shop.entity.Cart;
import project.shop.entity.CartProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class ReadCartResponse {

    private List<CartProductResponse> cartProducts;
    private Integer totalCount;
    private Integer totalPrice;

    public static ReadCartResponse createResponse(List<CartProduct> cartProducts, Cart cart) {

        ReadCartResponse response = new ReadCartResponse();
        response.cartProducts =  cartProducts.stream().map(cartProduct -> CartProductResponse.createResponse(cartProduct))
                .collect(Collectors.toList());
        response.totalPrice = cart.getTotalPrice();
        response.totalCount = cart.getTotalCount();
        return response;
    }

@Data
    static class CartProductResponse {

        private String name;
        private Integer price;
        private Integer count;

        public static CartProductResponse createResponse(CartProduct cartProduct) {

            CartProductResponse response = new CartProductResponse();
            response.name = cartProduct.getProduct().getName();
            response.price = cartProduct.getPrice();
            response.count = cartProduct.getCount();
            return response;
        }
    }
}
