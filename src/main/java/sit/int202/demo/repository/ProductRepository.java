package sit.int202.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
            SELECT p FROM Product p
                    WHERE p.productName LIKE %?1%
                    AND p.buyPrice BETWEEN ?2 AND ?3
                    ORDER BY p.buyPrice asc
            """)
    List<Product> findByNameAndBuyPrice(String productName, BigDecimal lower, BigDecimal upper);

    @Query("""
            SELECT p FROM Product p
                    WHERE p.productName LIKE %?1%
                    AND p.buyPrice BETWEEN ?2 AND ?3
                    ORDER BY p.buyPrice asc
            """)
    Page<Product> findByPage(String productName, BigDecimal lower, BigDecimal upper, Pageable pageable);
}
