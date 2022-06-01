package com.example.demo.controller;

import com.example.demo.dto.room.RoomTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@Api(value = "Shows the Hot Tour info", consumes = "application/json")
public class HotTourController {

    ConcurrentHashMap<Integer, Tour> hotTours = new ConcurrentHashMap<>();

    @ApiModel(value = "Hot Tour Room Model")
    public record Room(
            @ApiModelProperty(value = "Room number", example = "1") Integer id,
            @ApiModelProperty(value = "Room type") RoomTypeEnum roomType,
            @ApiModelProperty(value = "Date of room availability", required = true, example = "01-06-2022") @JsonFormat(pattern = "dd-MM-yyyy") LocalDate availabilityDate,
            @ApiModelProperty(value = "Room included stuff", allowEmptyValue = true, example = "[slippers, towels, shampoo, shower gel]") List<String> includedStuff) {
    }

    @ApiModel(value = "Hot Tour Location Model")
    public record Location(
            @ApiModelProperty(value = "Hot tour country location", example = "Vietnam") String country,
            @ApiModelProperty(value = "Hot tour city location", example = "Nha-Trang") String city) {
    }

    @ApiModel(value = "Hot Tour Model", description = "Sample model for the documentation")
    public record Tour(@ApiModelProperty(value = "Hot tour ID", example = "1") Integer id,
                       @ApiModelProperty(value = "Hot tour Location") Location location,
                       @ApiModelProperty(value = "Hot tour Room") Room room,
                       @ApiModelProperty(value = "Hot tour price in USD", example = "1000") Double price) {
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a hot tour",
            response = Tour.class,
            nickname = "getHotTour")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, response = Tour.class, message = "SUCCESS"),
                    @ApiResponse(code = 401, message = "UNAUTHORIZED"),
                    @ApiResponse(code = 403, message = "ACCESS IS DENIED"),
                    @ApiResponse(code = 403, message = "NOT FOUND"),
                    @ApiResponse(code = 500, message = "EXCEPTION ERROR")
            })
    @GetMapping("/{id}")
    public Tour getHotTour(@PathVariable @ApiParam(value = "Hot Tour ID", required = true, example = "1") Integer id) {
        return hotTours.get(id);
    }

    @GetMapping()
    public List<Tour> getHotTours() {
        return new ArrayList<>(hotTours.values());
    }

    @PostMapping()
    public Tour addHotTour(@RequestBody Tour hotTour) {
        hotTours.putIfAbsent(hotTour.id(), hotTour);
        return hotTour;
    }
}
