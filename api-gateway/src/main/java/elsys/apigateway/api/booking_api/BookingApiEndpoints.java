package elsys.apigateway.api.booking_api;

public class BookingApiEndpoints {
    private static final String baseUrl = System.getenv("BOOKING_SERVICE_API_URL");
    private static final String reservations = "/reservations";

    public static String requestReservation() {
        return baseUrl + reservations;
    }

    public static String getPendingReservationsByProperty(String propertyUuid) {
        return baseUrl + reservations + "/pending?propertyUuid=" + propertyUuid;
    }

    public static String updateReservationStatus(String reservationUuid) {
        return baseUrl + reservations + "/" + reservationUuid + "/status";
    }
}
