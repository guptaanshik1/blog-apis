package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult {

    private Boolean status;
    private String message;
    private Object result;

    public ServiceResult(Boolean status, Object result) {
        this.status = status;
        this.result = result;
    }
}
