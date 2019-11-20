package com.theme.park;

import com.theme.park.doa.ParkRepository;
import com.theme.park.doa.RoleRepository;
import com.theme.park.entities.Park;
import com.theme.park.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThemeParkApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ParkRepository parkRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(ThemeParkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role();
        role.setName("USER");
        Role role2 = new Role();
        role2.setName("ADMIN");
        roleRepository.save(role);
        roleRepository.save(role2);

        Park park = new Park();
        park.setName("Astérix");
        park.setURLFlag("https://www.countryflags.io/fr/shiny/64.png");
        parkRepository.save(park);

        Park park2 = new Park();
        park2.setName("Disney");
        park2.setURLFlag("https://www.countryflags.io/fr/shiny/64.png");
        parkRepository.save(park2);

        Park park3 = new Park();
        park3.setName("Europa Park");
        park3.setURLFlag("https://www.countryflags.io/de/shiny/64.png");
        parkRepository.save(park3);

        Park park4 = new Park();
        park4.setName("Europa Park2");
        park4.setURLFlag("https://www.countryflags.io/de/shiny/64.png");
        parkRepository.save(park4);
    }
}
