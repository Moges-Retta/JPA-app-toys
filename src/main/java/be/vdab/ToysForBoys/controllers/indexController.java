package be.vdab.ToysForBoys.controllers;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Product;
import be.vdab.ToysForBoys.domain.Status;
import be.vdab.ToysForBoys.services.OrderService;
import be.vdab.ToysForBoys.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class indexController {
    private final OrderService service;
    private final ProductService pservice;
    private LinkedHashSet<Integer> idsVanOrders;

    public indexController(OrderService service, ProductService pservice) {
        this.service = service;
        this.pservice = pservice;
        this.idsVanOrders=new LinkedHashSet<>();
    }

    @GetMapping
    public ModelAndView index() {
        var modelAndView = new ModelAndView("index");
        var statuses = Arrays.stream(Status.values())
                .filter(status -> status != Status.CANCELLED)
                .filter(status -> status != Status.SHIPPED)
                .collect(Collectors.toList());
        modelAndView.addObject("orders", service.findByStatus(statuses));
        modelAndView.addObject("IdsUpdateFailed", idsVanOrders);
        return modelAndView;
    }
    @PostMapping(value="setAsShipped")
    public String updateStatus(int[] ids) {
        if (ids != null) {
            Arrays.stream(ids).forEach(id->{
                var ordered = new LinkedList<Integer>();
                var products = new LinkedList<Product>();
                var orderIds = new LinkedList<Integer>();
                var oudeStatus = new LinkedList<Status>();
                var oudeShipped = new LinkedList<LocalDate>();
                var idsVanFailedOrders = new ArrayList<Integer>();
                var order = service.findById(id).get();

                orderIds.add(order.getId());
                products.addAll(order.getProducts());
                order.getOrderdetailSet().forEach(orderdetail -> ordered.add(orderdetail.getOrdered()));

                oudeStatus.add(order.getStatus());
                oudeShipped.add(order.getShipped());

                updateTables(id,products, orderIds, ordered, oudeStatus, oudeShipped,idsVanFailedOrders);
            });

        }
        return "redirect:/";
    }
    public void updateTables(int id,LinkedList<Product> products,LinkedList<Integer> orderIds,
                             LinkedList<Integer> ordered,LinkedList<Status> oudeStatus,
                             LinkedList<LocalDate> oudeShipped,ArrayList<Integer> idsVanFailedOrders){
        for(var i=0;i<products.size();i++){
            if(products.get(i).getInStock()<ordered.get(i)){
                idsVanFailedOrders.add(id);
                idsVanOrders.add(id);
            }
            /*else {
                products.get(i).updateInOrder(products.get(i).getInOrder() - ordered.get(i));
                products.get(i).updateInStock(products.get(i).getInStock() - ordered.get(i));
            }*/
        }
        if(idsVanFailedOrders.size()==0){
            for(var i=0;i<products.size();i++) {
                service.updateStatusValue(id, Status.SHIPPED);
                service.updateShippedValue(id, LocalDate.now());
                products.get(i).updateInOrder(products.get(i).getInOrder() - ordered.get(i));
                products.get(i).updateInStock(products.get(i).getInStock() - ordered.get(i));
            }
        }
        // als de voorraad niet genoeg is, de verandering van status and shipped zijn ongedaan
        /*if(idsVanOrders.size()!=0){
            for(var i=0;i<products.size();i++){
                var id = orderIds.getFirst();
                products.get(i).updateInOrder(products.get(i).getInOrder() + ordered.get(i));
                products.get(i).updateInStock(products.get(i).getInStock() + ordered.get(i));
                service.findById(id).get()
                        .updateStatus(oudeStatus.get(i));
                service.findById(id).get()
                        .updateShipped(oudeShipped.get(i));
            }
        }*/
    }
}