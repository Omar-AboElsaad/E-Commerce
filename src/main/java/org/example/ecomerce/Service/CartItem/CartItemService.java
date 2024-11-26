package org.example.ecomerce.Service.CartItem;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.CartItemDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.CartItem;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Repository.CartItemRepo;
import org.example.ecomerce.Repository.CartRepo;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.Product.ProductService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

//--------------------------------------------------------------------------------------------------
    //Add item To Cart by getting user cart by user ID

    @Override
    public void addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartService.getCartByUserId(userId);
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
        public void removeItemFromCart(Long userId, Long itemId) {
            Cart cart=cartService.getCartByUserId(userId);
       CartItem cartItem=cart.getCartItems()
               .stream()
               .filter(item->item.getProduct().getId().equals(itemId))
               .findFirst().orElseThrow(() -> new ResourceNotFoundException("There is no item with id "+itemId));
            cartService.removeItemFromCart(cart,cartItem);
       cartRepo.save(cart);
    }


//--------------------------------------------------------------------------------------------------


        @Override
        public void updateItemQuantity(Long userId, Long itemId, int Quantity) {
            Cart cart=cartService.getCartByUserId(userId);
            CartItem Item= cart.getCartItems()
                    .stream()
                    .filter(item->item.getProduct().getId().equals(itemId))
                    .findFirst().orElseThrow(() -> new ResourceNotFoundException("There is no Product With id "+itemId +" in Cart"));

            Item.setQuantity(Quantity);
            Item.setUnitPrice(Item.getProduct().getPrice());
            Item.setTotalPrice();

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
    public CartItemDto getCartItemByProductId(Long userId,Long productId){
        Cart cart=cartService.getCartByUserId(userId);
        CartItem cartItem= cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no Cart item with Id "+productId));
        return modelMapper.map(cartItem, CartItemDto.class);
    }

//--------------------------------------------------------------------------------------------------

//    @Override
    public List<CartItemDto> getAllCartItems(Long cartId){
        List<CartItem> cartItems=cartItemRepo.findAllByCart_id(cartId);
       return cartItems
                .stream()
                .map(item -> modelMapper
                        .map(item, CartItemDto.class)).toList();

    }

//--------------------------------------------------------------------------------------------------

    @Override
    public CartItemDto getCartItemByProductname(Long userId, String productName){
        Cart cart=cartService.getCartByUserId(userId);
       Product product=productService.getProductByName(productName);
        CartItem cartItem= cartItemRepo.findByProductIdAndCartId(product.getId(), cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no Cart item with name "+productName));
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}
