package com.report.nhomchot.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TicketDTO {
    private UUID id;
    private String qrImageURL;
    private SeatDTO seat;
    private BillDTO bill;
}
