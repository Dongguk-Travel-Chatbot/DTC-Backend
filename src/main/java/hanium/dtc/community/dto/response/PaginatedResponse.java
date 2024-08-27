package hanium.dtc.community.dto.response;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> content,
        int currentPage,
        int totalPages,
        long totalElements
) {}


