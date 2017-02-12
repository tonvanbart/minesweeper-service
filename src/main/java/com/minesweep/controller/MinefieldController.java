package com.minesweep.controller;

import com.minesweep.dto.MessageDto;
import com.minesweep.dto.MinefieldDto;
import com.minesweep.service.Minefield;
import com.minesweep.service.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the minefields.
 * Created by ton on 09/02/17.
 */
@RestController
public class MinefieldController {

    @RequestMapping(value = "/minefield", method = RequestMethod.POST)
    public String[] getHints(@RequestBody MinefieldDto minefieldDto) {
        Integer height = minefieldDto.getHeight();
        Integer width = minefieldDto.getWidth();
        Minefield minefield = Minefield.scan(height, width, minefieldDto.getMinefield());
        return minefield.getHints();
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto sendParseError(ParseException ex) {
        return new MessageDto(ex.getClass().getSimpleName(), ex.getMessage());
    }
}
