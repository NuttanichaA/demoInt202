package sit.int202.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.service.ProductService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("") //ถ้าเรียก /product แล้วไม่ต่อด้วยอะไรเลยจะเรียก method นี้
    public String getAllProducts(Model model){
                                        //เป็นชื่อที่ view เรียกใช้ ต้องเป็นชื่อนี้เท่านั้น
        model.addAttribute("products", service.findAll());
        return "product_list";
    }

    @GetMapping("/search")
    public String searchByContent(@RequestParam(required = true) String searchParam, Model model){
        model.addAttribute("products", service.findByAnyContentsUsingNameQuery(searchParam));
        return "product_list";
    }

    @GetMapping("/searchByPrice")           //ถ้าไม่ส่งมาจะใช้ค่านี้แทน
    public String searchByPrice(@RequestParam(defaultValue = "10.0") Double lower,
                                @RequestParam(defaultValue = "999") Double upper,Model model){
        model.addAttribute("products",
                service.findByPrice(BigDecimal.valueOf(lower), BigDecimal.valueOf(upper)));
        return "product_list";
    }

    @GetMapping("/page") //all products but paging
    public String getAllProductsPaging(@RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "10") int pageSize, Model model){
        model.addAttribute("page", service.findAll(PageRequest.of(pageNumber, pageSize)));
        return "product_list_paging";
    }

}
