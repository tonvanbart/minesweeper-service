package com.minesweep.service;

import com.minesweep.dto.MinefieldDto;
import com.minesweep.repository.InMemoryHintsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ton on 15/02/17.
 */
@Component
public class HintsService {

    private final InMemoryHintsRepository hintsRepository;

    private static final Logger LOG = LoggerFactory.getLogger(HintsService.class);

    @Autowired
    public HintsService(InMemoryHintsRepository hintsRepository) {
        this.hintsRepository = hintsRepository;
        LOG.info("initialized");
    }

    public String storeHints(MinefieldDto minefieldDto) {
        LOG.trace("storeHints()");
        Integer height = minefieldDto.getHeight();
        Integer width = minefieldDto.getWidth();
        String[] bombs = minefieldDto.getMinefield();

        Minefield minefield = Minefield.scan(height, width, bombs);

        return hintsRepository.store(minefield.getHints());
    }
}
