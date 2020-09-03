package be.vdab.ToysForBoys.controllers;

import be.vdab.ToysForBoys.domain.Orderdetail;
import be.vdab.ToysForBoys.domain.Status;
import be.vdab.ToysForBoys.services.OrderService;
import be.vdab.ToysForBoys.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class indexController {
    private final OrderService service;
    private final ProductService pservice;

    public indexController(OrderService service, ProductService pservice) {
        this.service = service;
        this.pservice = pservice;
    }

    @GetMapping
    public ModelAndView index() {
        var statuses = Arrays.stream(Status.values())
                .filter(status -> status != Status.CANCELLED)
                .filter(status -> status != Status.SHIPPED)
                .collect(Collectors.toList());
        return new ModelAndView("index", "orders", service.findByStatus(statuses));
    }
    @PostMapping(value="setAsShipped")
    public String updateStatus(int[] ids) {
        var nieuweInStock = new LinkedList<Integer>();
        var nieuweInOrder = new LinkedList<Integer>();
        var ordered = new LinkedList<Integer>();
        if (ids != null) {
            Arrays.stream(ids).forEach(id->{
                service.updateStatusValue(id, Status.SHIPPED);
                /*service.updateShippedValue(id, LocalDate.now());
                service.findById(id).get().getOrderdetailSet()
                        .forEach(orderdetail -> ordered.add(orderdetail.getOrdered()));
                pservice.findById(id).get().getOrderdetailSet()
                .forEach(orderdetail -> orderdetail.);*/

            });

        }
        return "redirect:/";
    }
}