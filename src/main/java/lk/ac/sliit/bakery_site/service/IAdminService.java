package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.model.Product;
import lk.ac.sliit.bakery_site.model.Reservation;

import java.util.Map;
import java.util.List;

public interface IAdminService {

    //RsevationPart
    List<Reservation> getReservations();

    Reservation updateReservationStatus(Long reservationId, String status);

    void deleteReservation(Long reservationId);

    List<Product> getPublicProducts();
}
