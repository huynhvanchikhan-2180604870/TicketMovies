package com.report.nhomchot.services;


import com.report.nhomchot.dto.BillDTO;
import com.report.nhomchot.dto.BookingRequestDTO;
import com.report.nhomchot.dto.TicketDTO;
import com.report.nhomchot.entities.Bill;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.entities.Ticket;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.repositories.IBillRepository;
import com.report.nhomchot.repositories.ISeatRepository;
import com.report.nhomchot.repositories.ITheaterRepository;
import com.report.nhomchot.repositories.ITicketRepository;
import com.report.nhomchot.repositories.repo.ShowTimeRepository;
import com.report.nhomchot.utils.config.VNPayService;
import com.report.nhomchot.utils.repo.IGeneralService;
import com.report.nhomchot.utils.repo.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService implements IGeneralService<BillDTO>, IBillService{
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ISeatRepository seatRepository;
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VNPayService paymentService;  // Handles VNPay integration
    @Override
    @Transactional
    public String createNewBill(BookingRequestDTO bookingRequestDTO,
                                HttpServletRequest request) throws RuntimeException {
        LocalDate date=LocalDate.now();
        LocalTime time=LocalTime.now();
        //Lấy ra lịch
        ShowTime showTime = showTimeService.getShowTimeById(bookingRequestDTO.getShowtime_id()).orElseThrow();
        if(showTime.getStartTime().compareTo(date)>0 ||
                (showTime.getStartTime().compareTo(date)==0 && showTime.getEndTime().compareTo(time)>0)	) {
            //Lấy ra người dùng
            User user = userRepository.getById(bookingRequestDTO.getUser_id());

            //Lưu Bill gồm thông tin người dùng xuống trước
            Bill billToCreate = new Bill();
            billToCreate.setUser(user);
            billToCreate.setId(UUID.randomUUID());
            billToCreate.setCreatedTime(LocalDateTime.now());
            billToCreate.setPrice(bookingRequestDTO.getPrice());
            Bill createdBill = billRepository.save(billToCreate);

            //Với mỗi ghế ngồi check xem đã có ai đặt chưa, nếu rồi thì throw, roll back luôn còn ko
            //thì đóng gói các thông tin ghế và lịch vào vé và lưu xuống db
            bookingRequestDTO.getList_seat_id().forEach(seatId->{
                if(!ticketRepository.findTicketsByShowtime_IdAndSeat_Id(showTime.getId(),seatId)
                        .isEmpty()){// Nếu đã có người đặt vé ghế đó ở lịch cụ thể đó thì
                    throw new RuntimeException("Đã có người nhanh tay hơn đặt ghế, mời bạn chọn lại!");
                }
                // đóng gói lịch, seat và bill vào từng vé rồi add vào list vé
                Ticket ticket = new Ticket();
                ticket.setId(UUID.randomUUID());
                ticket.setShowtime(showTime);
                ticket.setSeat(seatRepository.getById(seatId));
                ticket.setBill(createdBill);
                ticket.setQrImageURL("https://scontent-sin6-2.xx.fbcdn.net/v/t1.15752-9/268794058_655331555823095_3657556108194277679_n.png?_nc_cat=105&ccb=1-5&_nc_sid=ae9488&_nc_ohc=BrNXGO8HufkAX_OGjWc&_nc_ht=scontent-sin6-2.xx&oh=03_AVK_zaJj7pziY9nLrVqoIQJAzbomu4KPgED1PxFFpYfCrQ&oe=61F778D8");
                ticketRepository.save(ticket);
            });
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String paymentUrl = paymentService.createOrder(billToCreate.getPrice(), "Thanh Toán hóa đơn của: " + user.getFullname(),baseUrl);
            return paymentUrl; // Return the payment URL directly
        }else {
            throw new RuntimeException("lịch chiếu đã kết thúc không thể đặt chỗ ngồi!");
        }
//       return "null"; // Return the payment URL directly
    }

    @Override
    public List<BillDTO> findAll() {
        // TODO Auto-generated method stub
        return billRepository.findAll().stream().map(bill->modelMapper.map(bill, BillDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<BillDTO> findById(UUID billId) {
        Optional<Bill> bill = billRepository.findById(billId);
        return bill.map(t -> modelMapper.map(t, BillDTO.class));
    }


    @Override
    public void remove(UUID id) {
        // TODO Auto-generated method stub
        billRepository.deleteById(id);
    }


    @Override
    public BillDTO save(BillDTO billDTO) {
        Bill bill = modelMapper.map(billDTO, Bill.class);
        Bill savedBill = billRepository.save(bill);
        return modelMapper.map(savedBill, BillDTO.class);
    }

    public Bill add(Bill bill){
        return billRepository.save(bill);
    }

    public String processBooking(BookingRequestDTO bookingRequestDTO) throws Exception {
        ShowTime showTime = showTimeService.getShowTimeById(bookingRequestDTO.getShowtime_id())
                .orElseThrow(() -> new Exception("Showtime not found"));

        if (!isShowTimeValid(showTime)) {
            throw new Exception("The showtime has ended or is not valid.");
        }

        User user = userRepository.findById(bookingRequestDTO.getUser_id())
                .orElseThrow(() -> new Exception("User not found"));

        Bill bill = prepareBill(user, showTime); // This will save the bill and return the saved bill with ID
        List<Ticket> tickets = reserveSeats(showTime, bookingRequestDTO.getList_seat_id(), bill);

        String paymentUrl = paymentService.createOrder(bill.getPrice(), "Thanh Toán hóa đơn của: " + user.getFullname(),"http://localhost:8080/bill/payment-callback");
        return paymentUrl; // Return the payment URL directly
    }


    private boolean isShowTimeValid(ShowTime showTime) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        return showTime.getStartTime().compareTo(date) > 0 ||
                (showTime.getStartTime().compareTo(date) == 0 && showTime.getEndTime().compareTo(time) > 0);
    }

    private Bill prepareBill(User user, ShowTime showTime) {
        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setUser(user);
        bill.setCreatedTime(LocalDateTime.now());
        bill.setPrice(showTime.getPrice()); // Assuming getPrice() method exists
        return bill;
    }

    private List<Ticket> reserveSeats(ShowTime showTime, List<UUID> seatIds, Bill bill) throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        for (UUID seatId : seatIds) {
            if (!ticketRepository.findTicketsByShowtime_IdAndSeat_Id(showTime.getId(), seatId).isEmpty()) {
                throw new RuntimeException("Some seats are already booked.");
            }
            Ticket ticket = new Ticket();
            ticket.setShowtime(showTime);
            ticket.setSeat(seatRepository.findById(seatId).orElseThrow(() -> new Exception("Seat not found")));
            ticket.setBill(bill);
            tickets.add(ticket);
        }
        return tickets;
    }

    private void finalizeBooking(Bill bill, List<Ticket> tickets) {
        billRepository.save(bill);
        ticketRepository.saveAll(tickets);
    }

    public String handlePaymentCallback(Map<String, String> params) {
        // Implementation for handling VNPay callback
        // This should verify the payment and finalize the booking if successful
        return "Payment processed.";
    }
}
