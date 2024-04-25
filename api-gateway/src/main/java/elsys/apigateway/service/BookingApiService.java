package elsys.apigateway.service;

import java.util.List;

public interface BookingApiService {
    void requestReservation(Object reservationRequest);
    List<Object> getPendingReservationsByProperty(String propertyUuid);
    void updateReservationStatus(String reservationUuid, Object updateData);
}
