package com.mjc.school.service.dto;

import lombok.Data;


public interface BaseDto<K> {
    K getId();

    void setId(K id);

}
