package com.minesweep.controller;

import com.minesweep.dto.MessageDto;
import com.minesweep.dto.MinefieldDto;
import com.minesweep.service.HintsService;
import com.minesweep.service.Minefield;
import com.minesweep.service.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the minefields.
 * Created by ton on 09/02/17.
 */
@RestController
public class MinefieldController {

    private final HintsService hintsService;

    private static final Logger LOG = LoggerFactory.getLogger(MinefieldController.class);

    @Autowired
    public MinefieldController(HintsService hintsService) {
        this.hintsService = hintsService;
        LOG.info("initialized");
    }

    @RequestMapping(value = "/minefield", method = RequestMethod.POST)
    public String[] getHints(@RequestBody MinefieldDto minefieldDto) {
        Integer height = minefieldDto.getHeight();
        Integer width = minefieldDto.getWidth();
        Minefield minefield = Minefield.scan(height, width, minefieldDto.getMinefield());
        return minefield.getHints();
    }

    @RequestMapping(value = "/bombs", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createHints(@RequestBody MinefieldDto minefieldDto) {
        String hintsId = hintsService.storeHints(minefieldDto);
        Map<String, String> result = new HashMap<>();
        result.put("hintsId", hintsId);
        return result;
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto sendParseError(ParseException ex) {
        return new MessageDto(ex.getClass().getSimpleName(), ex.getMessage());
    }
}
