package com.report.nhomchot.dto;

import com.report.nhomchot.entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BillDTO {
    private UUID id;
    private LocalDateTime createdTime;
    private List<TicketDTO> listTickets;
    private User user;
}
