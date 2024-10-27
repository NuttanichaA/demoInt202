package sit.int202.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sit.int202.demo.entities.Product;
import sit.int202.demo.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> findAll(){
        return repository.findAll(); //return ทั้ง table
    }

    public Page<Product> findAll(Pageable pageable){
        return repository.findAll(pageable); //return เป็น page: ดีกว่า!!
    }

    public Page<Product> searchPage(String productName, BigDecimal lower, BigDecimal upper, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByPage(productName, lower, upper, pageable);
    }

    public List<Product> findByAnyContents(String anyContent){
        return repository.findProductsByProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(anyContent, anyContent, anyContent);
    }

    public List<Product> findByAnyContentsUsingNameQuery(String anyContent){
        return repository.findByAnyContents("%" + anyContent + "%"); //เราใช้ like กับ contain ดังนั้นต้องประกบด้วย %
    }

    public List<Product> findByPrice(BigDecimal lower, BigDecimal upper){
        if(lower.compareTo(upper) > 0){ //ถ้า lower > upper ก็สลับที่กัน
            BigDecimal temp = lower;
            lower = upper;
            upper = temp;
        }
        return repository.findProductsByBuyPriceBetweenOrderByBuyPriceDesc(lower, upper);
    }

    public List<Product> findByNameAndBuyPrice(String productName, BigDecimal lower, BigDecimal upper){
        if(lower.compareTo(upper) > 0){ //ถ้า lower > upper ก็สลับที่กัน
            BigDecimal temp = lower;
            lower = upper;
            upper = temp;
        }
        if(productName == null || productName.isEmpty()){
            return repository.findProductsByBuyPriceBetweenOrderByBuyPriceDesc(lower, upper);
        }else{
            return repository.findByNameAndBuyPrice(productName, lower, upper);
        }
    }
}
