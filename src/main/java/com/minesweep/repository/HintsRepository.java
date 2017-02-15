package com.minesweep.repository;

import com.minesweep.dto.HintsDto;

import java.util.Optional;

/**
 * Created by ton on 15/02/17.
 */
public interface HintsRepository {

    String store(String[] hints);

    Optional<HintsDto> get(String id);
}
