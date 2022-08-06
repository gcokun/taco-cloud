package gcokun.tacocloud.controller;

import gcokun.tacocloud.OrderProperties;
import gcokun.tacocloud.authentication.UserInformation;
import gcokun.tacocloud.repository.OrderRepository;
import gcokun.tacocloud.taco.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderProperties orderProperties;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderProperties orderProperties, OrderRepository orderRepository) {
        this.orderProperties = orderProperties;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @GetMapping("/allOrders")
    public String ordersForUser(@AuthenticationPrincipal UserInformation user, Model model) {
        Pageable pageable = PageRequest.of(0, orderProperties.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder,
                               Errors error,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal UserInformation user) {
        if (error.hasErrors()) {
            return "orderForm";
        }
        tacoOrder.setUser(user);
        orderRepository.save(tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    @ResponseBody
    public TacoOrder putOrder(@PathVariable("orderId") Long orderId,
                              @RequestBody TacoOrder tacoOrder) {
        tacoOrder.setId(orderId);
        return orderRepository.save(tacoOrder);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    @ResponseBody
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId,
                                @RequestBody TacoOrder patchOrder) {
        TacoOrder order = orderRepository.findById(orderId).get();
        if (patchOrder.getDeliveryName() != null) {
            order.setDeliveryName(patchOrder.getDeliveryName());
        } else if (patchOrder.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patchOrder.getDeliveryStreet());
        }
        //etc
        return orderRepository.save(order);
    }

    @DeleteMapping(path = "/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {

        }
    }

}
