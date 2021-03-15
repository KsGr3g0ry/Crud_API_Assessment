package com.example.demo.controller;

import com.example.demo.dao.DonutRepository;
import com.example.demo.model.Donut;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DonutController {
    private final DonutRepository repository;
    public DonutController(DonutRepository repository) {
        this.repository = repository;
    }



    @PostMapping("/donuts") //TODO post to create donut
    public Donut createDonut(@RequestBody Donut donuts){
        return repository.save(donuts);
    }

    @GetMapping("/donuts/{id}") //TODO get to grab one donut
    public Optional<Donut> grabDonut(@PathVariable Long id){
        return repository.findById(id);
    }

    @PatchMapping("/donuts/{id}") //TODO patch to update attributes
    public Donut changeDonut(@RequestBody Donut donuts, @PathVariable Long id){
        if(repository.existsById(id)){
            Donut oldDonut = repository.findById(id).get();
            oldDonut.setTopping(donuts.getTopping());
            oldDonut.setName(donuts.getName());
            oldDonut.setExpiration(donuts.getExpiration());
            return repository.save(oldDonut);
        } else{
            return repository.save(donuts);
        }
    }

    @DeleteMapping("/donuts/{id}") //TODO delete a donut
    public String removeDonut(@PathVariable Long id){
        repository.deleteById(id);
        return "Donut has been deleted";
    }

    @GetMapping("/donuts") //TODO get all the donuts
    public Iterable<Donut> seeAllDonuts(){
        return repository.findAll();
    }


}
