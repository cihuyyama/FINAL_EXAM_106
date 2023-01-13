/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_exam.final_exam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Muhammad iqbal al habib
 * @NIM 20200140106
 */
@RestController
@CrossOrigin
@RequestMapping("exam")
public class myController {

    Surat data = new Surat();
    SuratJpaController ctrl = new SuratJpaController();

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Surat> getNama() {
        List<Surat> temp = new ArrayList<>();
        try {
            temp = ctrl.findSuratEntities();
        } catch (Exception e) {
            temp = (List<Surat>) e;
        }
        return temp;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public String sendData(HttpEntity<String> datasend, @RequestBody Surat surat) throws JsonProcessingException {
        surat.setTimeStamp(new Date());
        ObjectMapper export = new ObjectMapper();
        String feedback = "Data";
        data = export.readValue(datasend.getBody(), Surat.class);
        try {
            ctrl.create(data);
            feedback = data.getJudul()+ " saved";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public String putData(HttpEntity<String> datasend) throws JsonProcessingException {
        ObjectMapper export = new ObjectMapper();
        String feedback = "Data";
        data = export.readValue(datasend.getBody(), Surat.class);
        try {
            ctrl.edit(data);
            feedback = data.getJudul()+ " edited";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> datasend) throws JsonProcessingException {
        ObjectMapper export = new ObjectMapper();
        String feedback = "Data";
        data = export.readValue(datasend.getBody(), Surat.class);
        try {
            feedback = data.getJudul()+ " deleted";
            ctrl.destroy(data.getId());
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }

}
