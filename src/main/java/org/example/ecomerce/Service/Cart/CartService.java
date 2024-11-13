package org.example.ecomerce.Service.Cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.CartDto;
import org.example.ecomerce.DTO.CartItemDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.CartItem;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Repository.CartItemRepo;
import org.example.ecomerce.Repository.CartRepo;
import org.example.ecomerce.Service.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

//--------------------------------------------------------------------------------------------------


    @Override
    public CartDto getCartDTOByUserId(Long userId) {
        Cart cart= cartRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Cart For User "+ userId));

        CartDto cartDto= convertCartToCartDto(cart);
        cartDto.setCartItems(new HashSet<>(convertCartItemToCartItemDto(cart.getCartItems())) );
        return cartDto;
    }

    //--------------------------------------------------------------------------------------------------


    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Cart For User "+ userId));
    }



//--------------------------------------------------------------------------------------------------


    @Override
    public Cart createNewCart(Long userId){
        User user=userService.getUserById(userId);
        Cart cart=new Cart();
        cart.setUser(user);
        cartRepo.save(cart);
        return cart;
    }


//--------------------------------------------------------------------------------------------------


    @Override
    public Cart getcart(Long cartId) {
        return cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Cart with Id "+cartId));

    }

//--------------------------------------------------------------------------------------------------


    @Transactional
    @Override
    public void clearCart(Long cartId) {
        Cart cart = getcart(cartId);
        cartItemRepo.deleteAllByCart_id(cartId);
        cart.getCartItems().clear();
       updateCartTotalAmount(cart);

    }

//--------------------------------------------------------------------------------------------------


    //This Method Delete CartItself and with using Cascade.All Cart Items will also Delete
    @Override
    public void deleteCart(Long id){
        cartRepo.deleteById(id);
    }


//--------------------------------------------------------------------------------------------------

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart  cart=getcart(cartId);
        return cart.getTotalAmounts();
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void addItemToCart(Cart cart, CartItem newItem){
        Set<CartItem> cartItems=cart.getCartItems();
        cartItems.add(newItem);
        newItem.setCart(cart);
        updateCartTotalAmount(cart);
    }

//--------------------------------------------------------------------------------------------------


    @Override
    public void removeItemFromCart(Cart cart, CartItem item){
        Set<CartItem> cartItems=cart.getCartItems();
        cartItems.remove(item);
        item.setCart(null);
        cart.setCartItems(cartItems);
        updateCartTotalAmount(cart);

    }

//---------------------------------------------------------------------------------------------------


    //This Method Used in anywhere we change in cart items because it recalculates Total Prices for all Cart Items
    @Override
    public void updateCartTotalAmount(Cart cart) {
        Set<CartItem> cartItems = cart.getCartItems();
        BigDecimal Totalamount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            Totalamount = Totalamount.add((cartItem.getUnitPrice()).multiply(new BigDecimal(cartItem.getQuantity())));
        }
        cart.setTotalAmounts(Totalamount);
    }

    private List<CartItemDto> convertCartItemToCartItemDto(Set<CartItem>cartItems){
       return cartItems.stream().map(item -> modelMapper.map(item, CartItemDto.class)).toList();
    }

    private CartDto convertCartToCartDto(Cart cart){
        return modelMapper.map(cart, CartDto.class);

    }



}
