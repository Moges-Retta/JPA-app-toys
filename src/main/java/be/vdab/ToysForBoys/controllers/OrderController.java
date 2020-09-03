package be.vdab.ToysForBoys.controllers;

import be.vdab.ToysForBoys.domain.Orderdetail;
import be.vdab.ToysForBoys.domain.Product;
import be.vdab.ToysForBoys.services.OrderService;
import be.vdab.ToysForBoys.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService service;
    private final ProductService pservice;

    public OrderController(OrderService service, ProductService pservice) {
        this.service = service;
        this.pservice = pservice;
    }
    @GetMapping("{id}")
    public ModelAndView toonOrder(@PathVariable int id){
        var modelAndView = new ModelAndView("orderDetail");
        var products = new LinkedList<Product>();

        /*service.findById(id).get().getOrderdetailSet()
                .forEach(orderdetail -> products.add(orderdetail.getProduct()));*/
        modelAndView.addObject("orders",service.findById(id).get());
        modelAndView.addObject("products",products);
        /*modelAndView.addObject("totaalPrijs",service.findById(id).get()
                .totalValue());*/

        return modelAndView;
    }
}
