package com.report.nhomchot.services;

import com.report.nhomchot.entities.Category;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.repositories.ITheaterRepository;
import com.report.nhomchot.repositories.repo.CategoryRepository;
import com.report.nhomchot.repositories.repo.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TheaterService {
    @Autowired
    private ITheaterRepository theaterRepository;

    public TheaterService(){}

    public List<Theater> getAllTheater(){
        return theaterRepository.findAll();
    }

    public Page<Theater> getAllTheater(String name, int page, int size, String sortName, String sortDirect){
        Pageable pageable = PageRequest.of(page, size);
        return theaterRepository.findAll(TheaterRepository.search(name, sortName, sortDirect), pageable);
    }

    public Theater addTheater(Theater theater){
        return theaterRepository.save(theater);
    }

    public Optional<Theater> getTheaterById(UUID id){
        return theaterRepository.findById(id);
    }

    public Theater updateTheater(Theater theater){
        Theater existingTheater = theaterRepository.findById(theater.getId())
                .orElseThrow(() -> new IllegalStateException("theater with ID " +
                        theater.getId() + " does not exist."));
        existingTheater.setCinema(theater.getCinema());
        existingTheater.setName(theater.getName());
        existingTheater.setSeating_capacity(theater.getSeating_capacity());
        existingTheater.setSeats(theater.getSeats());
        existingTheater.setShowtimes(theater.getShowtimes());
        return theaterRepository.save(existingTheater);
    }

    public void deleteTheater(UUID id){
        if (!theaterRepository.existsById(id)) {
            throw new IllegalStateException("Theater with ID " + id + " does not exist.");
        }
        theaterRepository.deleteById(id);
    }

    public List<Theater> findAllByCinemaId(UUID cinemaId) {
        return theaterRepository.findByCinemaId(cinemaId);
    }
}
