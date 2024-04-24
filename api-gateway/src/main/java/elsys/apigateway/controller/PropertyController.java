package elsys.apigateway.controller;

import elsys.apigateway.service.PropertyApiService;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyApiService propertyApiService;

    @PostMapping()
    public ResponseEntity<Object> addProperty(
        @RequestPart("data") Object propertyData,
        @RequestPart("images") MultipartFile[] images
    ) {
        System.out.println("PropertyController.addProperty");
        return ResponseEntity.ok(propertyApiService.addProperty(propertyData, images));
    }

    @GetMapping("/{propertyUuid}/rooms/available")
    public ResponseEntity<List<Object>> getAvailableRooms(
        @RequestParam("checkInDate") @Future LocalDate checkInDate,
        @RequestParam("checkOutDate") @Future LocalDate checkOutDate,
        @RequestParam("numberOfGuests") @Positive int numberOfGuests,
        @PathVariable String propertyUuid
    ) {
        return ResponseEntity.ok(propertyApiService.getAvailableRooms(propertyUuid, checkInDate, checkOutDate, numberOfGuests));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<Object>> getOwnerProperties() {
        return ResponseEntity.ok(propertyApiService.getOwnerProperties());
    }

    @GetMapping("/location")
    public ResponseEntity<Object> getPropertiesByCityAndCountry(
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "country") String country,
        @RequestParam int page,
        @RequestParam int size
    ) {
        return ResponseEntity.ok(propertyApiService.getPropertiesByCityAndCountry(city, country, page, size));
    }
}
