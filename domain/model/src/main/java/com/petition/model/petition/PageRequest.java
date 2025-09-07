package com.petition.model.petition;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PageRequest {
    private int page;
    private int size;
}
