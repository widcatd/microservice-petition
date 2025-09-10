package com.petition.model.petition;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PageResponse<T> {
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private List<T> content;
}
