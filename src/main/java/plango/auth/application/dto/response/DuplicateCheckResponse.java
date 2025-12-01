package plango.auth.application.dto.response;

public record DuplicateCheckResponse(
        boolean available,
        String field,
        String value
) {
}
