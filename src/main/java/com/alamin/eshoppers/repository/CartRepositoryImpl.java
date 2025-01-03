package com.alamin.eshoppers.repository;

import com.alamin.eshoppers.domain.Cart;
import com.alamin.eshoppers.domain.Order;
import com.alamin.eshoppers.domain.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CartRepositoryImpl implements CartRepository{
    private static final Map<User, Set<Cart>> CARTS = new ConcurrentHashMap<>();

    private OrderRepository orderRepository = new OrderRepositoryImpl();
    @Override
    public Optional<Cart> findByUser(User currentUser) {

//        Set<Cart> carts = CARTS.get(currentUser);
//        if (carts != null && ! carts.isEmpty()) {
//            Cart cart = (Cart) carts.toArray()[carts.size() -1];
//            return Optional.of(cart);
//        }
//        return Optional.empty();

        var usersCart = getCart(currentUser);
        if (usersCart.isPresent()) {
            var cart = usersCart.get();
            var orders = orderRepository.findOrderByUser(currentUser);

            if (isOrderAlreadyPlacedWith(orders, cart)) {
                return Optional.empty();
            } else {
                return Optional.of(cart);
            }
        }

        return  Optional.empty();
    }


    private boolean isOrderAlreadyPlacedWith(Set<Order> orderByUser, Cart cart) {
        return orderByUser.stream().anyMatch(order -> order.getCart().equals(cart));
    }

    private Optional<Cart> getCart(User currentUser) {
        var carts = CARTS.get(currentUser);

        if (carts != null && ! carts.isEmpty()) {
            Cart cart = (Cart) carts.toArray()[carts.size() - 1];
            return Optional.of(cart);
        }

        return Optional.empty();
    }

    @Override
    public Cart save(Cart cart) {

        User u = cart.getUser();
        if (u == null) {
            throw new IllegalArgumentException("Cart's user cannot be null.");
        }

        CARTS.computeIfPresent(cart.getUser() ,
                (user, carts) -> {carts.add(cart);
            return carts ;
        });

        CARTS.computeIfAbsent(cart.getUser(),
               user -> {
                    var carts = new LinkedHashSet<Cart>();
                    carts.add(cart);
                    return carts;
               });


        return cart ;
    }

    @Override
    public Cart update(Cart cart) {
        CARTS.computeIfPresent(cart.getUser(),
                ((user, carts) -> {
                    Cart[] objects = carts.toArray(Cart[] :: new) ;
                    objects[objects.length-1] = cart ;
                    return new LinkedHashSet<>(Arrays.asList(objects));
                }));

        return cart ;
    }
}
