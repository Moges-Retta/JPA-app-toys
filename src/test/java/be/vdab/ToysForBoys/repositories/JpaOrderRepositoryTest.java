package be.vdab.ToysForBoys.repositories;

import be.vdab.ToysForBoys.domain.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaOrderRepository.class)
@Sql({"/insertCountry.sql","/insertCustomer.sql","/insertOrder.sql"})
public class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
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
