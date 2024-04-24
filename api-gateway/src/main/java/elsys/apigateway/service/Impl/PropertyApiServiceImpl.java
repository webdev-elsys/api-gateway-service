package elsys.apigateway.service.Impl;

import elsys.apigateway.api.property_api.PropertyApiEndpoints;
import elsys.apigateway.service.PropertyApiService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class PropertyApiServiceImpl implements PropertyApiService {
    private final WebClient webClient;

    public PropertyApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }


    // TODO: FIX THIS (the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is null)
    @Override
    public Object addProperty(Object propertyData, MultipartFile[] images) {
        String endpoint = PropertyApiEndpoints.addProperty();

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("data", propertyData);
        formData.add("images", images);


        return webClient.post()
                .uri(endpoint)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    @Override
    public List<Object> getAvailableRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate, int guests) {
        String endpoint = PropertyApiEndpoints.getAvailableRooms(propertyUuid, checkInDate, checkOutDate, guests);

        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();
    }

    @Override
    public List<Object> getOwnerProperties() {
        String ownerUuid = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        String endpoint = PropertyApiEndpoints.getOwnerProperties(ownerUuid);

        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();
    }

    @Override
    public Object getPropertiesByCityAndCountry(String city, String country, int page, int size) {
        String endpoint = PropertyApiEndpoints.getPropertiesByCityAndCountry(city, country, page, size);

        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
