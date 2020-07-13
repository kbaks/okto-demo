package eu.acme.demo.repository;

import eu.acme.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByClientReferenceCode(String clientReferenceCode);

    // As is explained here https://stackoverflow.com/a/15360333/13461573
    // this performs one query when eager loading of items is required
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.id = (:id)")
    Optional<Order> findByIdAndFetchItemsEagerly(@Param("id") UUID id);

}
