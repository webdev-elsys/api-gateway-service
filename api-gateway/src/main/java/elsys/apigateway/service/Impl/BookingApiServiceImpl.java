package elsys.apigateway.service.Impl;

import elsys.apigateway.api.booking_api.BookingApiEndpoints;
import elsys.apigateway.service.BookingApiService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BookingApiServiceImpl implements BookingApiService {
    private final WebClient webClient;

    public BookingApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public void requestReservation(Object reservationRequest) {
        String endpoint = BookingApiEndpoints.requestReservation();

        webClient
            .post()
            .uri(endpoint)
            .bodyValue(reservationRequest)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public List<Object> getPendingReservationsByProperty(String propertyUuid) {
        String endpoint = BookingApiEndpoints.getPendingReservationsByProperty(propertyUuid);

        return webClient
            .get()
            .uri(endpoint)
            .retrieve()
            .bodyToFlux(Object.class)
            .collectList()
            .block();
    }

    @Override
    public void updateReservationStatus(String reservationUuid, Object updateData) {
        String endpoint = BookingApiEndpoints.updateReservationStatus(reservationUuid);

        webClient
            .patch()
            .uri(endpoint)
            .bodyValue(updateData)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
