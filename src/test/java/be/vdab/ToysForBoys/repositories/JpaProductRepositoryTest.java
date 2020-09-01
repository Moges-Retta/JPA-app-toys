package be.vdab.ToysForBoys.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DataJpaTest
@Sql({"/insertProductline.sql","/insertProduct.sql"})
@Import(JpaProductRepository.class)
class JpaProductRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private JpaProductRepository repository;
    private EntityManager manager;

    public JpaProductRepositoryTest(JpaProductRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }
    public int idVanTestOrder(){
        return super.jdbcTemplate.queryForObject(
                "select id from products where name ='test'", Integer.class);
    }
    @Test
    void findById(){
        assertThat(repository.findById(idVanTestOrder()).get().getName())
                .isEqualTo("test");
    }
    @Test
    @DisplayName("geen record voor id =  -1")
    void findByIdOnbestandeId(){
        assertThat(repository.findById(-1)).isNotPresent();
    }
    @Test
    void updateInstock(){
        var id = idVanTestOrder();
        repository.updateInstock(id,2);
        assertThat(repository.findById(id).get().getInStock())
                .isEqualTo(2);
    }
    @Test
    void updateInorder(){
        var id = idVanTestOrder();
        repository.updateInorder(id,2);
        assertThat(repository.findById(id).get().getInOrder())
                .isEqualTo(2);
    }
    @Test
    void updateNegatiefInstockMislukt(){
        var id = idVanTestOrder();
        assertThatIllegalArgumentException().isThrownBy(()->repository.updateInstock(id, -2));
    }
    @Test
    void updateNegatiefInorderMislukt(){
        var id = idVanTestOrder();
        assertThatIllegalArgumentException().isThrownBy(()->repository.updateInorder(id, -2));
    }

}
