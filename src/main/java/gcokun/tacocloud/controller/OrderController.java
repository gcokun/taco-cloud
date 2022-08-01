package gcokun.tacocloud.controller;

import gcokun.tacocloud.OrderProperties;
import gcokun.tacocloud.authentication.Users;
import gcokun.tacocloud.repository.OrderRepository;
import gcokun.tacocloud.taco.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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
    public String ordersForUser(@AuthenticationPrincipal Users user, Model model) {
        Pageable pageable = PageRequest.of(0, orderProperties.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder,
                               Errors error,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal Users user) {
        if (error.hasErrors()) {
            return "orderForm";
        }
        tacoOrder.setUser(user);
        orderRepository.save(tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
