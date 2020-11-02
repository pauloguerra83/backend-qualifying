
package com.backend.qualifyng.backendqualifyng.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class HotelDTO {

    private Integer id;
    private String city;
    private String name;
    private List<RoomDTO> rooms = null;

}
