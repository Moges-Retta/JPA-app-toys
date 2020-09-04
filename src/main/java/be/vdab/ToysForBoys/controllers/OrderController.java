package be.vdab.ToysForBoys.controllers;

import be.vdab.ToysForBoys.queryresults.OrderTable;
import be.vdab.ToysForBoys.services.OrderService;
import be.vdab.ToysForBoys.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        var order = service.findById(id).get();
        var modelAndView = new ModelAndView("orderDetail");
        var prijsPerProduct = new LinkedList<BigDecimal>();
        var prijs = new LinkedList<BigDecimal>();
        var orderTable = new LinkedList<OrderTable>();
        var ordered = new LinkedList<Integer>();
        var orderdetails = new LinkedList<>(order.getOrderdetailSet());
        var productName = new LinkedList<String>();
        var productInStock = new LinkedList<Integer>();


        orderdetails
                .forEach(orderdetail->{
                            prijsPerProduct.add(orderdetail.getPriceEach()
                                    .multiply(BigDecimal.valueOf((orderdetail.getOrdered()))));
                            prijs.add(orderdetail.getPriceEach());
                            ordered.add(orderdetail.getOrdered());
                            productName.add(orderdetail.getProduct().getName());
                            productInStock.add(orderdetail.getProduct().getInStock());

                });

        for(var i=0;i<prijs.size();i++){
            orderTable.add(new OrderTable(productName.get(i),prijs.get(i),ordered.get(i),
                    prijs.get(i).multiply(BigDecimal.valueOf(ordered.get(i))), productInStock.get(i)));
        }
        modelAndView.addObject("orders",order);
        modelAndView.addObject("orderTables",orderTable);
        modelAndView.addObject("totaalPrijs",prijsPerProduct.stream().reduce(BigDecimal::add).get());
        return modelAndView;
    }
}
