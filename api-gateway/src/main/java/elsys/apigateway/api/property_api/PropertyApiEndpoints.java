package elsys.apigateway.api.property_api;

public class PropertyApiEndpoints {
    private static final String baseUrl = System.getenv("PROPERTY_SERVICE_API_URL");
    private static final String properties = "/properties";

    public static String addProperty() {
        return baseUrl + properties;
    }

    public static String getAvailableRooms(String propertyUuid) {
        return baseUrl + properties + "/" + propertyUuid + "/rooms/available";
    }

    public static String getOwnerProperties() {
        return baseUrl + properties + "/owner";
    }

    public static String getPropertiesByCityAndCountry(String city, String country, int page, int size) {
        return baseUrl + properties + "/location?city=" + city + "&country=" + country + "&page=" + page + "&size=" + size;
    }
}
