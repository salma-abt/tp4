package ma.mundia.patients_mvc;

import ma.mundia.patients_mvc.entities.Patient;
import ma.mundia.patients_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "YASSINE", new Date(), false,123));
            patientRepository.save(new Patient(null, "Yassmine", new Date(), true,212));
            patientRepository.save(new Patient(null, "mohammed", new Date(), false,721));
            patientRepository.save(new Patient(null, "Yahya", new Date(), true,439));
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
    }
}
