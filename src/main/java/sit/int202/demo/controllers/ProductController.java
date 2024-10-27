package sit.int202.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.entities.Product;
import sit.int202.demo.service.ProductService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("") //ถ้าเรียก /product แล้วไม่ต่อด้วยอะไรเลยจะเรียก method นี้
    public String getAllProducts(Model model) {
        //เป็นชื่อที่ view เรียกใช้ ต้องเป็นชื่อนี้เท่านั้น
        model.addAttribute("products", service.findAll());
        return "product_list";
    }

    @GetMapping("/search")
    public String searchByContent(@RequestParam(required = true) String searchParam, Model model) {
        model.addAttribute("products", service.findByAnyContentsUsingNameQuery(searchParam));
        return "product_list";
    }

    @GetMapping("/searchByPrice")           //ถ้าไม่ส่งมาจะใช้ค่านี้แทน
    public String searchByPrice(@RequestParam(defaultValue = "10.0") Double lower,
                                @RequestParam(defaultValue = "999") Double upper, Model model) {
        model.addAttribute("products",
                service.findByPrice(BigDecimal.valueOf(lower), BigDecimal.valueOf(upper)));
        return "product_list";
    }

    @GetMapping("/searchByNameAndPrice")
    public String searchByNameAndPrice(@RequestParam(required = false) String searchParam,
                                       @RequestParam(defaultValue = "10.0") Double lower,
                                       @RequestParam(defaultValue = "999") Double upper, Model model) {
        model.addAttribute("products", service.findByNameAndBuyPrice(searchParam, BigDecimal.valueOf(lower), BigDecimal.valueOf(upper)));
        return "product_list";
    }

    //    @GetMapping("/page") //all products but paging
//    public String getAllProductsPaging(@RequestParam(defaultValue = "0") int pageNumber,
//                                       @RequestParam(defaultValue = "10") int pageSize, Model model) {
//        model.addAttribute("page", service.findAll(PageRequest.of(pageNumber, pageSize)));
//        return "product_list_paging";
//    }
//
//    @GetMapping("/searchPage")
//    public String searchPage(@RequestParam(required = false) String searchParam,
//                             @RequestParam(defaultValue = "10.0") Double lower,
//                             @RequestParam(defaultValue = "999") Double upper,
//                             @RequestParam(defaultValue="0") int pageNumber,
//                             @RequestParam(defaultValue = "10") int pageSize, Model model) {
//        Page<Product> page = service.searchPage(searchParam, BigDecimal.valueOf(lower), BigDecimal.valueOf(upper), pageNumber, pageSize);
//        model.addAttribute("products", page);
//        return "product_list_paging";
//    }
    @GetMapping("/page") // Combined endpoint for listing and searching
    public String getProducts(
            @RequestParam(required = false) String searchParam,
            @RequestParam(required = false) Double lower,
            @RequestParam(required = false) Double upper,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {

        Page<Product> page;

        if ((searchParam != null && !searchParam.isEmpty()) || (lower != null && upper!= null)) {
            if(lower == null || upper == null){
                lower = 10.0;
                upper = 999.0;
            }
            page = service.searchPage(searchParam, BigDecimal.valueOf(lower), BigDecimal.valueOf(upper), pageNumber, pageSize);
        } else {
            // List all products
            page = service.findAll(PageRequest.of(pageNumber, pageSize));
        }

        model.addAttribute("page", page);
        model.addAttribute("searchParam", searchParam); // Add searchParam to the model for the form
        model.addAttribute("lower", lower); // Add lower price to the model
        model.addAttribute("upper", upper); // Add upper price to the model

        return "product_list_paging";
    }

}
