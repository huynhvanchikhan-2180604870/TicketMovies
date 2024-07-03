package com.report.nhomchot.services;

import com.report.nhomchot.dto.BillDTO;
import com.report.nhomchot.dto.BookingRequestDTO;
import com.report.nhomchot.utils.repo.IGeneralService;
import jakarta.servlet.http.HttpServletRequest;

public interface IBillService extends IGeneralService<BillDTO> {
    String createNewBill(BookingRequestDTO bookingRequestDTO, HttpServletRequest request) throws RuntimeException;


}