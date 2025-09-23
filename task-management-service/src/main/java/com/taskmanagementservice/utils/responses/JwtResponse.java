package com.taskmanagementservice.utils.responses;

import com.taskmanagementservice.utils.dto.JwtDto;

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
