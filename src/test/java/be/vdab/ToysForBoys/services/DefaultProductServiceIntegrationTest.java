package be.vdab.ToysForBoys.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(DefaultProductService.class)
@ComponentScan(value = "be.vdab.ToysForBoys.repositories",
        resourcePattern = "JpaProductRepository.class")
@Sql({"/insertProductline.sql","/insertProduct.sql"})
public class DefaultProductServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final DefaultProductService service;
    private final EntityManager manager;

    public DefaultProductServiceIntegrationTest(DefaultProductService service, EntityManager manager) {
        this.service = service;
        this.manager = manager;
    }
    private int idVanTestProduct() {
        return super.jdbcTemplate.queryForObject(
                "select id from products where name ='test'", Integer.class);
    }
    @Test
    void updateInstockValue(){
        var id = idVanTestProduct();
        service.updateInstockValue(id, 5);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select inStock from products where id=?", Integer.class, id))
                .isEqualByComparingTo(5);
    }
    @Test
    void updateInorderValue(){
        var id = idVanTestProduct();
        service.updateInorderValue(id, 5);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select inOrder from products where id=?", Integer.class, id))
                .isEqualByComparingTo(5);
    }
}
