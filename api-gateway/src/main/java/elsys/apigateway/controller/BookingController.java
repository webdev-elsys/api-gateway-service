package elsys.apigateway.controller;

import elsys.apigateway.service.BookingApiService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class BookingController {
    private final BookingApiService bookingApiService;

    @PostMapping()
    public ResponseEntity<Void> requestReservation(@RequestBody Object reservationRequest) {
        bookingApiService.requestReservation(reservationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Object>> getPendingReservationsByProperty(@RequestParam("propertyUuid") @UUID String propertyUuid) {
        return ResponseEntity.ok(bookingApiService.getPendingReservationsByProperty(propertyUuid));
    }

    @PatchMapping("/{reservationUuid}/status")
    public ResponseEntity<Void> updateReservationStatus(
        @PathVariable @UUID String reservationUuid,
        @RequestBody Object updateData
    ) {
        bookingApiService.updateReservationStatus(reservationUuid, updateData);
        return ResponseEntity.ok().build();
    }
}
