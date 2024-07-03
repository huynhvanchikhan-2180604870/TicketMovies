package com.report.nhomchot.services;

import com.report.nhomchot.dto.ShowTimeDTO;
import com.report.nhomchot.dto.TicketDTO;
import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.entities.Ticket;
import com.report.nhomchot.repositories.IShowTimeRepository;
import com.report.nhomchot.repositories.repo.MovieRepository;
import com.report.nhomchot.repositories.repo.ShowTimeRepository;
import com.report.nhomchot.utils.repo.IGeneralService;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShowTimeService implements IGeneralService<ShowTimeDTO>, IShowtimeService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IShowTimeRepository showTimeRepository;
    private final WebClient webClient;

    @Autowired
    public ShowTimeService(WebClient webClientBuilder) {
        this.webClient = webClientBuilder;
    }

    public List<ShowTime> getAllShowTime(){
        return showTimeRepository.findAll();
    }
    public Page<ShowTime> getAllShowTime(String name, int page, int size, String sortName, String sortDirect){
        Pageable pageable = PageRequest.of(page, size);
        return showTimeRepository.findAll(ShowTimeRepository.search(name, sortName, sortDirect), pageable);
    }

    public Optional<ShowTime>getShowTimeById(UUID id){
        return showTimeRepository.findById(id);
    }

    public Mono<ShowTimeDTO> getShowTimeByIdApi(UUID id) {
        return webClient.get()
                .uri("/api/show-times/get-by-id/{id}", id)
                .retrieve()
                .bodyToMono(ShowTimeDTO.class);
    }

    public ShowTime addShowTime(ShowTime showTime){
        return showTimeRepository.save(showTime);
    }

    public ShowTime updateShowTime(@NotNull ShowTime showTime){
        ShowTime existingShowTime = showTimeRepository.findById(showTime.getId())
                .orElseThrow(() -> new IllegalStateException("showTime with ID " +
                        showTime.getId() + " does not exist."));
        existingShowTime.setTickets(showTime.getTickets());
        existingShowTime.setPrice(showTime.getPrice());
        existingShowTime.setStartTime(showTime.getStartTime());
        existingShowTime.setEndTime(showTime.getEndTime());
        existingShowTime.setTheater(showTime.getTheater());
        existingShowTime.setMovie(showTime.getMovie());
        return showTimeRepository.save(existingShowTime);
    }


    public void deleteCinema(UUID id){
        if (!showTimeRepository.existsById(id)) {
            throw new IllegalStateException("existingShowTime with ID " + id + " does not exist.");
        }
        showTimeRepository.deleteById(id);
    }

    @Override
    public List<String> getAllStartDateShowTime() {
        LocalDate date = LocalDate.now();
        return showTimeRepository.getAllStartDateShowTime()
                .stream()
                .filter(localDate -> localDate.compareTo(date) >= 0)
                .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .collect(Collectors.toList());
    }


    @Override
    public List<ShowTimeDTO> findAll() {
        return showTimeRepository.findAll()
                .stream().map(schedule -> {
                    ShowTimeDTO s=modelMapper.map(schedule,ShowTimeDTO.class);
                    s.setMovie_id(s.getMovie_id());
                    s.setTheater_id(s.getTheater_id());
                    return s;
                })
                .collect(Collectors.toList());
    }


    @Override
    public ShowTimeDTO save(ShowTimeDTO showTimeDTO) {
        ShowTime showTime = modelMapper.map(showTimeDTO, ShowTime.class);
        ShowTime savedShowTime = showTimeRepository.save(showTime);
        return modelMapper.map(savedShowTime, ShowTimeDTO.class);
    }



    @Override
    public void remove(UUID id) {
        showTimeRepository.deleteById(id);
    }


    @Override
    public Optional<ShowTimeDTO> findById(UUID id) {
        Optional<ShowTime> showTime = showTimeRepository.findById(id);
        return showTime.map(t -> modelMapper.map(t, ShowTimeDTO.class));
    }
}
