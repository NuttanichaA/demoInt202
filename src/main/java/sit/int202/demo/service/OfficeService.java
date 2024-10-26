package sit.int202.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.demo.entities.Office;
import sit.int202.demo.repository.OfficeRepository;

import java.util.List;

@Service
public class OfficeService {
    //@Autowired แต่มันเหลือง เพราะ field injection ไม่แนะนำ แนะนำให้ใช้ constructor injection แทน
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    //ตรงนี้อาจจะให้ return เป็นอย่างอื่นก็ได้ ex. set array
    public List<Office> getAllOffices(){
        return officeRepository.findAll();
    }

    public Office getOffice(String officeCode){
        return officeRepository.findById(officeCode).orElse(null);
        //เวลาเราค้นหา มันมีโอกาสที่จะไม่เจอ ดังนั้นเราต้องจัดการต่อด้วย orElse ถ้าไม่เจอจะ...
    }

    public Office addOffice(Office office){
        if(office.getOfficeCode() == null || officeRepository.existsById(office.getOfficeCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  //status code 400
                    String.format("Office id '%s' already exists",
                    office.getOfficeCode()));
        }
        return officeRepository.save(office);
    }

    public Office updateOffice(Office office){
        if(!officeRepository.existsById(office.getOfficeCode())  || office.getOfficeCode() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Office id '%s' does not exists",
                            office.getOfficeCode()));
        }
        return officeRepository.save(office);
    }

    public Office deleteOffice(String officeCode){
        Office office = getOffice(officeCode);
        if(office == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Office id '%s' does not exists",
                            officeCode));
        }
        officeRepository.deleteById(officeCode);
        return office;
    }
}
