package gcokun.tacocloud.controller;

import gcokun.tacocloud.OrderAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private OrderAdminService orderAdminService;

    @Autowired
    public AdminController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @PostMapping("/deleteOrders")
    public String deleteOrders() {
        orderAdminService.deleteAllOrders();
        return "redirect:/admin";
    }

}
