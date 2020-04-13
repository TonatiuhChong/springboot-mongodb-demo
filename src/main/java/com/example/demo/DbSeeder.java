package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private HotelRepository hotelRepository;
    public DbSeeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }



    @Override
    public void run(String... args) throws Exception {
        Hotel marriot= new Hotel(
                "Marriot",
                150,
                new Address("Paris","France"),
                        Arrays.asList(
                                new Review("Tonatiuh", 9, false),
                                new Review("Maria",10,true)
                        )
        );
        Hotel ibis= new Hotel(
                "Ibis",
                150,
                new Address("Bucharest","Romania"),
                Arrays.asList(
                        new Review("Larry", 19, true),
                        new Review("Conche",10,false)
                )
        );

        Hotel sofitel= new Hotel(
                "Sofitel",
                500,
                new Address("Munich","Germany"),
                Arrays.asList(
                        new Review("Eins", 1, true),
                        new Review("Zwei",2,true)
                )
        );

        //drop all hotels
        this.hotelRepository.deleteAll();

        //Add our hotels
        List<Hotel> hotels= Arrays.asList(marriot,ibis,sofitel);
        this.hotelRepository.saveAll(hotels);
    }
}
