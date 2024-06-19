package com.report.nhomchot.services;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.repositories.ICinemaRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaService {
    @Autowired
    private ICinemaRepository cinemaRepository;

    public CinemaService() {}

    public List<Cinema> getAllCimema(){
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(UUID id){
        return cinemaRepository.findById(id);
    }

    public Cinema addCinema(Cinema cinema){
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(@NotNull Cinema cinema){
        Cinema existingCinema = cinemaRepository.findById(cinema.getId())
                .orElseThrow(() -> new IllegalStateException("cinema with ID " +
                        cinema.getId() + " does not exist."));
        existingCinema.setName(cinema.getName());
        existingCinema.setLocation(cinema.getLocation());
        existingCinema.setTotal_theaters(cinema.getTotal_theaters());
        return cinemaRepository.save(existingCinema);
    }
    public void deleteCinema(UUID id){
        if (!cinemaRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        cinemaRepository.deleteById(id);
    }
}
