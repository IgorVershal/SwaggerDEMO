package com.example.demo.dto.room;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Room Type Model", description = "Sample model for the room types")
public enum RoomTypeEnum {
    SingleRoom, DoubleRoom
}
