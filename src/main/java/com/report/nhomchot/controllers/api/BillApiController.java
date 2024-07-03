package com.report.nhomchot.controllers.api;

import com.report.nhomchot.dto.BookingRequestDTO;
import com.report.nhomchot.entities.Bill;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.services.BillService;
import com.report.nhomchot.services.IBillService;
import com.report.nhomchot.services.ShowTimeService;
import com.report.nhomchot.utils.config.VNPayService;
import com.report.nhomchot.utils.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
public class BillApiController {
//    @Autowired
//    private BillService billService;
//    @Autowired
//    private IBillService ibillService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ShowTimeService showTimeService;
//    @Autowired
//    private VNPayService vnPayService;
//
//    @PostMapping("/create-new-bill")
//    public ResponseEntity<String> createNewBill(@RequestBody BookingRequestDTO bookingRequestDTO) {
//        try {
//            LocalDateTime dateTime = LocalDateTime.now();
//            Bill bill = new Bill();
//            bill.setId(UUID.randomUUID());
//            User user = userService.findById(bookingRequestDTO.getUser_id()).orElseThrow();
//            ShowTime showtime = showTimeService.getShowTimeById(bookingRequestDTO.getShowtime_id()).orElseThrow();
//            bill.setUser(user);
//            bill.setPrice(showtime.price);
//            bill.setCreatedTime(dateTime);
//
//            // Check payment method
//            if ("COD".equals(bookingRequestDTO.getPaymentMethod())) {
//                // Handle COD payment
//                ibillService.createNewBill(bookingRequestDTO); // Assuming createNewBill accepts a Bill object
//                return new ResponseEntity<>("Bạn đã mua vé xem phim thành công!", HttpStatus.OK);
//            } else if ("VNPay".equals(bookingRequestDTO.getPaymentMethod())) {
//                // Handle VNPay payment
//                String paymentUrl = vnPayService.createPaymentUrl(bill); // Assuming vnPayService can create a URL for payment
//                return ResponseEntity.ok(paymentUrl); // Send URL to client for redirection to VNPay
//            } else {
//                throw new IllegalArgumentException("Unsupported payment method");
//            }
//        } catch (IllegalArgumentException | NoSuchElementException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
//        }
//    }
@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewBill(@RequestBody BookingRequestDTO bookingRequestDTO, HttpServletRequest request) {
        try {
            String result = bookingService.createNewBill(bookingRequestDTO,request);
            return ResponseEntity.ok(Collections.singletonMap("paymentUrl", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @GetMapping("/payment-callback")
    public ResponseEntity<?> paymentCallback(@RequestParam Map<String, String> params) {
        try {
            String result = bookingService.handlePaymentCallback(params);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

}
