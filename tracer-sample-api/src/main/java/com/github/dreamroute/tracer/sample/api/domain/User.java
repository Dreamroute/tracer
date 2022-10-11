package com.github.dreamroute.tracer.sample.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String name;
}