package sit.int202.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int202.demo.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    //Query methods
    List<Product> findProductsByBuyPriceBetweenOrderByBuyPriceDesc(BigDecimal lower, BigDecimal upper);
    List<Product> findProductsByProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(String productName, String productCode, String productDescription);

    //Named Queries
    @Query("""
            select p from Product p where p.productCode like ?1
            or p.productName like ?1
            or p.productDescription like ?1
           """)
    List<Product> findByAnyContents(String searchParam);
}
