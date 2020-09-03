package be.vdab.ToysForBoys.repositories;

import be.vdab.ToysForBoys.domain.Orderdetail;
import be.vdab.ToysForBoys.domain.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaOrderRepository.class)
@Sql({"/insertCountry.sql","/insertCustomer.sql","/insertOrder.sql",
        "/insertProductline.sql","/insertProduct.sql","/insertOrderdetails.sql"})
class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String ORDERS = "orders";

    private JpaOrderRepository repository;
    private EntityManager manager;

    public JpaOrderRepositoryTest(JpaOrderRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }
    public int idVanTestOrder(){
        return super.jdbcTemplate.queryForObject(
                "select id from orders where comments ='test'", Integer.class);
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanTestOrder()).get().getOrdered())
                .isEqualTo(LocalDate.of(2001,1,1));
    }
    @Test
    @DisplayName("geen record voor id =  -1")
    void findByIdOnbestandeId(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void findByStaus(){
        var statuses = List.of(Status.values());
        assertThat(repository.findByStaus(statuses))
                .hasSize(super.countRowsInTableWhere(ORDERS,
                        "status in ('PROCESSING', 'WAITING', 'RESOLVED', 'CANCELLED', 'DISPUTED', 'SHIPPED')"));
    }
    @Test
    void OrderDetailsLezen(){
        repository.findById(idVanTestOrder()).get().getOrderdetailSet()
                .forEach(orderdetail -> {
                    assertThat(orderdetail.getOrdered().equals(BigDecimal.ZERO));
                    assertThat(orderdetail.getPriceEach().equals(BigDecimal.ZERO));
                });
    }
    @Test
    void ProductLezen(){
        repository.findById(idVanTestOrder()).get().getProducts()
                .forEach(product -> {
                    assertThat(product.getName().equals("test"));
                    assertThat(product.getPrice().equals(BigDecimal.ZERO));
                });
    }
    @Test
    void updateStatus(){
        var id = idVanTestOrder();
        repository.updateStatus(id,Status.PROCESSING);
        assertThat(repository.findById(id).get().getStatus())
                .isEqualTo(Status.PROCESSING);
    }
    @Test
    void updateShipped(){
        var id = idVanTestOrder();
        var date = LocalDate.of(2001,1,1);
        repository.updateShipped(id,date);
        assertThat(repository.findById(id).get().getShipped())
                .isEqualTo(date);
    }
}
