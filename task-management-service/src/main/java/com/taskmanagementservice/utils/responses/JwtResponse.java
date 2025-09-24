package com.taskmanagementservice.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taskmanagementservice.utils.dto.JwtDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse extends CommonResponse{

    private JwtDto jwtDto;

    public JwtDto getJwtDto() {
        return jwtDto;
    }

    public void setJwtDto(JwtDto jwtDto) {
        this.jwtDto = jwtDto;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "jwtDto=" + jwtDto +
                '}';
    }
}
