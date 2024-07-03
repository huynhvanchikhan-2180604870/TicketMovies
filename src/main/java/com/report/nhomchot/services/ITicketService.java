package com.report.nhomchot.services;

import com.report.nhomchot.dto.TicketDTO;
import com.report.nhomchot.utils.repo.IGeneralService;

import java.util.List;
import java.util.UUID;

public interface ITicketService extends IGeneralService<TicketDTO> {
    List<TicketDTO> getTicketsByUserId(UUID userId);

    List<TicketDTO> getTicketsByBillId(UUID billId);
}
