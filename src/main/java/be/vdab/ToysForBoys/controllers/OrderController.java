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
        var modelAndView = new ModelAndView("orderDetail");
        var prijsPerProduct = new ArrayList<BigDecimal>();
        var prijs = new ArrayList<BigDecimal>();
        var orderTable = new LinkedList<OrderTable>();
        var ordered = new ArrayList<Integer>();


        var products = new LinkedList<>(service.findById(id).get().getProducts());
        var orderdetails = new LinkedList<>(service.findById(id).get().getOrderdetailSet());

        orderdetails
                .forEach(orderdetail->prijsPerProduct.add(orderdetail.getPriceEach().multiply(new BigDecimal(orderdetail.getOrdered()))));
        orderdetails
                .forEach(orderdetail->prijs.add(orderdetail.getPriceEach()));
        orderdetails
                .forEach(orderdetail->ordered.add(orderdetail.getOrdered()));

        for(var i=0;i<prijs.size();i++){
            orderTable.add(new OrderTable(products.get(i).getName(),prijs.get(i),ordered.get(i),
                    prijs.get(i).multiply(new BigDecimal(ordered.get(i))), products.get(i).getInStock()));
        }

        modelAndView.addObject("orders",service.findById(id).get());
        modelAndView.addObject("orderTables",orderTable);

        modelAndView.addObject("totaalPrijs",prijsPerProduct.stream().reduce(BigDecimal::add).get());

        return modelAndView;
    }
}
