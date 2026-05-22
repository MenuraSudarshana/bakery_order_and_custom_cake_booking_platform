package lk.ac.sliit.bakery_site.controller;

import lk.ac.sliit.bakery_site.dto.ReservationRequestDto;
import lk.ac.sliit.bakery_site.service.IReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/reservations")
public class ReservationController {

    private final IReservationService reservationService;

    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public String createReservation(@RequestBody ReservationRequestDto request) {
        return reservationService.createReservation(request);
    }
}
