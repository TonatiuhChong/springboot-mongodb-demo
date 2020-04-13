package com.example.demo;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Hotels")
public class HotelController {
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    private HotelRepository hotelRepository;

    @GetMapping("/all")
    public List<Hotel> getAll(){
        List<Hotel>hotels=this.hotelRepository.findAll();
        return hotels;
    }

    @PutMapping
    public void insert(@RequestBody Hotel hotel){
        this.hotelRepository.insert(hotel);

    }

    @PostMapping
    public void update(@RequestBody Hotel hotel){
        this.hotelRepository.save(hotel);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id){
        this.hotelRepository.deleteById(id);

    }

    @GetMapping("/{id}")
    public Optional<Hotel> getById(@PathVariable("id") String id){
        Optional<Hotel> hotel=this.hotelRepository.findById(id);
        return hotel;
    }

    @GetMapping("/price/{maxPrice}")
    public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice){
        List<Hotel> hotels=this.hotelRepository.findByPricePerNightLessThan(maxPrice);
        return hotels;
    }

    @GetMapping("/address/{city}")
    public  List<Hotel> getByCity(@PathVariable("city") String city){
        List<Hotel> hotels=this.hotelRepository.findByCity(city);
        return hotels;
    }

    @GetMapping("/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country){
        //create a query class (QHotel)
        QHotel qHotel=new QHotel("hotel");
        //Using Query class, create the filter
        BooleanExpression filterByCountry=qHotel.address.country.eq(country);
        //Pass the filter to findAll method
        List<Hotel>hotels=(List <Hotel>)this.hotelRepository.findAll(filterByCountry);
        return hotels;
    }
    @GetMapping("/recommended")
    public List<Hotel> getRecommended(){
        final int maxPrice=160;
        final int minRating=7;
        QHotel qHotel=new QHotel("hotel");
        BooleanExpression filterByPrice=qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating=qHotel.reviews.any().rating.gt(minRating);
//        BooleanExpression filterByPrice=qHotel.pricePerNight.lt(maxPrice);
//        BooleanExpression filterByRating=qHotel.reviews.any().rating.gt(minRating);
        List<Hotel>hotels=(List <Hotel>)this.hotelRepository.findAll(filterByPrice.and(filterByRating));

        return  hotels;
    }

}
