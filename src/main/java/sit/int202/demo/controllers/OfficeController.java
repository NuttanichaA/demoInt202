package sit.int202.demo.controllers;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.entities.Office;
import sit.int202.demo.service.OfficeService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/offices")
public class OfficeController {

//    @Autowired //ดึง obj มา
//    private OfficeRepository repository;
    //controller ไม่ควรเรียก repo

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping("/all")          //กลไกที่ส่งไปหา view
    public String allOffices(Model model){
        List<Office> officeList = officeService.getAllOffices();
                                        //ชื่อที่จะไปใช้บน view
        model.addAttribute("offices", officeList);
        return "office_list";
    }

    @GetMapping("")             //ตรงนี้สามารถ config ได้ @RequestParam("ชื่อตัวแปร")
    public String getOfficeById(@RequestParam String officeCode, Model model){
        //ตอนเราส่งมาเรามี request parameter ติดมาด้วย (officecode)
        Office office = officeService.getOffice(officeCode);
        model.addAttribute("office", office);
        return "office_details";
    }

    @GetMapping("/delete")
    public String deleteOfficeById(@RequestParam String officeCode, Model model){
        Office office = officeService.deleteOffice(officeCode);
        model.addAttribute("office", office);
        model.addAttribute("message", "Office deleted successfully");
        return "office_details";
    }

    @GetMapping("/create") //ตอนคลิกลิ้งค์ต้องเอาฟอร์มกลับไป
    public String createForm(){
        return "create_form";
    }

    @PostMapping("/create") //แต่ตอน submit จะต้อง post แทน
    public void createOffice(Office office, HttpServletResponse response) throws IOException {
        officeService.addOffice(office);
        response.sendRedirect("/offices/all"); //พอทำงานที่ฝั่ง server เสร็จแล้ว จะส่ง response ว่าง ๆ ไปที่ browser แล้วบังคับให้ browser ส่ง url ที่ใส่ไปกลับมา
    }

    @GetMapping("/update") //ตอนคลิกลิ้งค์ต้องเอาฟอร์มกลับไป
    public String updateForm(@RequestParam String officeCode, Model model){
        Office office = officeService.getOffice(officeCode);
        model.addAttribute("office", office);
        return "update_form";
    }

    @PostMapping("/update") //แต่ตอน submit จะต้อง post แทน
    public void updateOffice(Office office, HttpServletResponse response) throws IOException {
        officeService.updateOffice(office);
        response.sendRedirect("/offices/all"); //พอทำงานที่ฝั่ง server เสร็จแล้ว จะส่ง response ว่าง ๆ ไปที่ browser แล้วบังคับให้ browser ส่ง url ที่ใส่ไปกลับมา
    }


}
