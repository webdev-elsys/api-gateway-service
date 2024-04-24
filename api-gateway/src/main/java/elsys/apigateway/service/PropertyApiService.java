package elsys.apigateway.service;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface PropertyApiService {
    Object addProperty(Object propertyData, MultipartFile[] images);
    List<Object> getAvailableRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate, int guests);
    List<Object> getOwnerProperties();
    Object getPropertiesByCityAndCountry(String city, String country, int page, int size);
}
