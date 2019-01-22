package com.captech.ioteam.machine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository repository;

}
