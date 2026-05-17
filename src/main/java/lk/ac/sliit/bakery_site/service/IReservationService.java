package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.ReservationRequestDto;

public interface IReservationService {

    String createReservation(ReservationRequestDto request);
}