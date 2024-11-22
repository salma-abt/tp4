package ma.mundia.patients_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.mundia.patients_mvc.entities.Patient;
import ma.mundia.patients_mvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name="page",defaultValue ="0")int page,
                           @RequestParam(name="size",defaultValue ="5")int size,
                           @RequestParam(name="keyword",defaultValue ="")String keyword
                           ) {
        Page<Patient> Pagepatients = patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients", Pagepatients.getContent());
        model.addAttribute("pages", new int[Pagepatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }


    @GetMapping("/admin/delete")
    public String delete (Long id,String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/formPatients")
     public String formPatients (Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatients";
}
    @PostMapping("/admin/save")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index";
    }
    @GetMapping("/admin/editPatient")
    public String editPatient(@RequestParam(name = "id") Long id, Model model){
        Patient patient=patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }
}
