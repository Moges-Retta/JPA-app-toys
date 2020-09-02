package be.vdab.ToysForBoys.services;

import be.vdab.ToysForBoys.domain.Order;
import be.vdab.ToysForBoys.domain.Status;
import be.vdab.ToysForBoys.exceptions.OrderNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DefaultOrderService.class)
@ComponentScan(value = "be.vdab.ToysForBoys.repositories",
        resourcePattern = "JpaOrderRepository.class")
@Sql({"/insertCountry.sql","/insertCustomer.sql","/insertOrder.sql"})
class DefaultOrderServiceTestIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final DefaultOrderService service;
    private final EntityManager manager;

    public DefaultOrderServiceTestIntegrationTest(DefaultOrderService service, EntityManager manager) {
        this.service = service;
        this.manager = manager;
    }
    private int idVanTestOrder() {
        return super.jdbcTemplate.queryForObject(
                "select id from orders where comments ='test'", Integer.class);
    }
    @Test
    public void updateStatusValue() {
        var id = idVanTestOrder();
        service.updateStatusValue(id, Status.DISPUTED);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select status from orders where id=?", Status.class, id))
                .isInstanceOf(Status.class)
        .isEqualByComparingTo(Status.DISPUTED);
    }

    @Test
    public void updateShippedValue() {
        var id = idVanTestOrder();
        var date = LocalDate.of(2001,1,1);
        service.updateShippedValue(id,date);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select shipped from orders where id=?", LocalDate.class, id))
                .isEqualTo(date);
    }
}
