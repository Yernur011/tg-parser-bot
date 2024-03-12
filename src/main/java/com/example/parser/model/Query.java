package com.example.parser.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    private String queryText;
    private String queryCity;
    private String queryCategory;
}
