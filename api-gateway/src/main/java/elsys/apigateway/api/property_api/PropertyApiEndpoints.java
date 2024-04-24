package elsys.apigateway.api.property_api;

import java.time.LocalDate;

public class PropertyApiEndpoints {
    private static final String baseUrl = System.getenv("PROPERTY_SERVICE_API_URL");
    private static final String properties = "/properties";

    public static String addProperty() {
        return baseUrl + properties;
    }

    public static String getAvailableRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate, int guests) {
        return baseUrl + properties + "/" + propertyUuid + "/rooms/available?checkInDate=" + checkInDate + "&checkOutDate=" + checkOutDate + "&numberOfGuests=" + guests;
    }

    public static String getOwnerProperties(String ownerUuid) {
        return baseUrl + properties + "/owner/" + ownerUuid;
    }

    public static String getPropertiesByCityAndCountry(String city, String country, int page, int size) {
        return baseUrl + properties + "/location?city=" + city + "&country=" + country + "&page=" + page + "&size=" + size;
    }
}
