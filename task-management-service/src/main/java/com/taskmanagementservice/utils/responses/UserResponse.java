package com.taskmanagementservice.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taskmanagementservice.utils.dto.UserDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends CommonResponse{

    private UserDto userDto;
    private List<UserDto> userDtoList;
    private Page<UserDto> userDtoPage;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<UserDto> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }

    public Page<UserDto> getUserDtoPage() {
        return userDtoPage;
    }

    public void setUserDtoPage(Page<UserDto> userDtoPage) {
        this.userDtoPage = userDtoPage;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userDto=" + userDto +
                ", userDtoList=" + userDtoList +
                ", userDtoPage=" + userDtoPage +
                '}';
    }
}
