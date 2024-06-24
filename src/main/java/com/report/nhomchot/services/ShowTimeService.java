package com.report.nhomchot.services;

import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.repositories.IShowTimeRepository;
import com.report.nhomchot.repositories.repo.MovieRepository;
import com.report.nhomchot.repositories.repo.ShowTimeRepository;
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
public class ShowTimeService {
    @Autowired
    private IShowTimeRepository showTimeRepository;

    public ShowTimeService(){}

    public List<ShowTime> getAllShowTime(){
        return showTimeRepository.findAll();
    }
    public Page<ShowTime> getAllShowTime(String name, int page, int size, String sortName, String sortDirect){
        Pageable pageable = PageRequest.of(page, size);
        return showTimeRepository.findAll(ShowTimeRepository.search(name, sortName, sortDirect), pageable);
    }

    public Optional<ShowTime> getShowTimeById(UUID id){
        return showTimeRepository.findById(id);
    }

    public ShowTime addShowTime(ShowTime showTime){
        return showTimeRepository.save(showTime);
    }

    public ShowTime updateShowTime(@NotNull ShowTime showTime){
        ShowTime existingShowTime = showTimeRepository.findById(showTime.getId())
                .orElseThrow(() -> new IllegalStateException("showTime with ID " +
                        showTime.getId() + " does not exist."));
        existingShowTime.setMovie_id(showTime.getMovie_id());
        existingShowTime.setPrice(showTime.getPrice());
        existingShowTime.setStartTime(showTime.getStartTime());
        existingShowTime.setEndTime(showTime.getEndTime());
        existingShowTime.setTheater_id(showTime.getTheater_id());

        return showTimeRepository.save(existingShowTime);
    }
    public void deleteCinema(UUID id){
        if (!showTimeRepository.existsById(id)) {
            throw new IllegalStateException("existingShowTime with ID " + id + " does not exist.");
        }
        showTimeRepository.deleteById(id);
    }
}
