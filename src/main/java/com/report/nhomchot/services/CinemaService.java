package com.report.nhomchot.services;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.models.CinemaModel;
import com.report.nhomchot.repositories.ICinemaRepository;
import com.report.nhomchot.repositories.repo.CinemaRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaService {
    @Autowired
    private ICinemaRepository cinemaRepository;

    public CinemaService() {}

    public Page<Cinema> getAllCinema(String name, int page, int size, String sortName, String sortDirect){
        Pageable pageable = PageRequest.of(page, size);
        return cinemaRepository.findAll(CinemaRepository.search(name, sortName, sortDirect), pageable);
    }

    public List<Cinema> getAllCinema(){
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
