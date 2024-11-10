package org.example.ecomerce.Service.CartItem;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.CartItem;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Repository.CartItemRepo;
import org.example.ecomerce.Repository.CartRepo;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.Product.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;
    private final ProductService productService;
    private final CartService cartService;

//--------------------------------------------------------------------------------------------------


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getcart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if (cartItem.getId() != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());

        }
        cartItem.setTotalPrice();
        cartService.addItemToCart(cart,cartItem);
        cartRepo.save(cart);
    }

//--------------------------------------------------------------------------------------------------

        @Override
        public void removeItemFromCart(Long cartId, Long itemId) {
            Cart cart=cartService.getcart(cartId);
       CartItem cartItem=cart.getCartItems()
               .stream()
               .filter(item->item.getProduct().getId().equals(itemId))
               .findFirst().orElseThrow(() -> new ResourceNotFoundException("There is no item with id "+itemId));
            cartService.removeItemFromCart(cart,cartItem);
       cartRepo.save(cart);
    }


//--------------------------------------------------------------------------------------------------


        @Override
        public void updateItemQuantity(Long cartId, Long itemId, int Quantity) {
            Cart cart=cartService.getcart(cartId);
            cart.getCartItems()
                    .stream()
                    .filter(item->item.getProduct().getId().equals(itemId))
                    .findFirst().ifPresent(item->{
                        item.setQuantity(Quantity);
                        item.setUnitPrice(item.getProduct().getPrice());
                        item.setTotalPrice();
                    });
          BigDecimal TotalAmount=cart.getCartItems().stream().map(cartItem ->
          {
              BigDecimal unitPrice=cartItem.getUnitPrice();
              if(unitPrice==null){
                  return BigDecimal.ZERO;
              }
              return unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
          }).reduce(BigDecimal.ZERO,BigDecimal::add);
            cart.setTotalAmounts(TotalAmount);
            cartRepo.save(cart);

        }


//--------------------------------------------------------------------------------------------------

    @Override
    public CartItem getCartItemByProductId(Long cartId,Long productId){
        return cartItemRepo.findByProductIdAndCartId(productId, cartId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Cart item with Id "+productId));

    }

//--------------------------------------------------------------------------------------------------

    @Override
    public List<CartItem> getAllCartItems(){
        return cartItemRepo.findAll();

    }

}
