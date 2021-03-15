package com.example.demo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dao.DonutRepository;
import com.example.demo.model.Donut;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.configuration.IMockitoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.awt.*;
//import java.util.Date;
import java.sql.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class DonutTest {
    @Autowired
    DonutRepository repository;
    @Autowired
    MockMvc mvc;
    //String json = ("{\"name\": \"glazed\",\"topping\": \"sugar\",\"expiration\": \"2020-03-17\"}");

    @Test
    @Transactional
    @Rollback //TODO post to donuts
    public void addDonut() throws Exception{
        MockHttpServletRequestBuilder request = post("/donuts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"glazed\",\"topping\": \"sugar\",\"expiration\": \"2020-03-17\"}");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("glazed")))
                .andExpect(jsonPath("$.topping",is("sugar")))
                .andExpect(jsonPath("$.expiration",is("2020-03-17")));
    }

    @Test
    @Transactional
    @Rollback //TODO get one specific donut
    public void grabDonut() throws Exception{
        Donut sprinkle = new Donut();
        sprinkle.setExpiration(Date.valueOf("2020-04-12"));
        sprinkle.setName("sprinkle");
        sprinkle.setTopping("pink and green sprinkles");

        Donut addSprinkle = this.repository.save(sprinkle);
        MockHttpServletRequestBuilder request = get("/donuts/" + addSprinkle.getId());

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("sprinkle")))
                .andExpect(jsonPath("$.topping",is("pink and green sprinkles")))
                .andExpect(jsonPath("$.expiration",is("2020-04-12")));

    }

    @Test
    @Transactional
    @Rollback //TODO patch to one donut
    public void updateDonut() throws Exception{
        Donut maple = new Donut();
        maple.setExpiration(Date.valueOf("2020-04-01"));
        maple.setName("maples");
        maple.setTopping("maple glazes");

        Donut updatedMaple = repository.save(maple);
        MockHttpServletRequestBuilder request = patch("/donuts/" + updatedMaple.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"maple\",\"topping\": \"maple glaze\",\"expiration\": \"2020-04-01\"}");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("maple")))
                .andExpect(jsonPath("$.topping",is("maple glaze")))
                .andExpect(jsonPath("$.expiration",is("2020-04-01")));
    }

    @Test
    @Transactional
    @Rollback //TODO delete one donut
    public void removeOneDonut() throws Exception{
        Donut creamFilled = new Donut();
        creamFilled.setTopping("cream filled with powder sugar");
        creamFilled.setName("cream filled");
        creamFilled.setExpiration(Date.valueOf("2002-01-26"));

        Donut nastyCreamFilled = repository.save(creamFilled);
        MockHttpServletRequestBuilder request = delete("/donuts/" + nastyCreamFilled.getId());
        this.mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    public void seeAllDonuts() throws Exception{
        Donut allDonuts = new Donut();
        allDonuts.setExpiration(Date.valueOf("2020-03-15"));
        allDonuts.setTopping("cinnamon");
        allDonuts.setName("cinnamon twist");
        repository.save(allDonuts);
        MockHttpServletRequestBuilder request = get("/donuts")
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("cinnamon twist")))
                .andExpect(jsonPath("$.topping",is("cinnamon")))
                .andExpect(jsonPath("$.expiration",is("2020-03-15")));
    }


}
