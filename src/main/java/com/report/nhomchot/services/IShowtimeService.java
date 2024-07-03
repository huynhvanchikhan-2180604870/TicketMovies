package com.report.nhomchot.services;

import com.report.nhomchot.dto.ShowTimeDTO;
import com.report.nhomchot.utils.repo.IGeneralService;

import java.util.List;

public interface IShowtimeService extends IGeneralService<ShowTimeDTO> {
    List<String> getAllStartDateShowTime();
}
