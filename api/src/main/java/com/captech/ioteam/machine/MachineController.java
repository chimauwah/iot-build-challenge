package com.captech.ioteam.machine;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/machines")
public class MachineController {

    private final MachineRepository repository;

    @RequestMapping(value = "printos", method = RequestMethod.GET)
    public List<PrintOSMachine> getAllPrintOSMachines() {
        List<PrintOSMachine> machines = new ArrayList<>();
        this.repository
                .findAll()
                .forEach(m -> machines.add(new PrintOSMachine(m)));
        return machines;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Machine> getAll() {
        List<Machine> machines = new ArrayList<>();
        this.repository.findAll().forEach(machines::add);
        return machines
                .stream()
                .sorted(Comparator.comparing(Machine::getId))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Machine getOne(@PathVariable("id") long id) {
        Machine machine = this.repository.findOne(id);
        return machine;
    }

    @RequestMapping(value = "{id}/refill", method = RequestMethod.GET)
    public Machine executeRefill(@PathVariable("id") long id) {
        Machine machine = this.repository.findOne(id);
        machine.setQuantity(machine.getCapacity());
        this.repository.save(machine);
        return machine;
    }

    @RequestMapping(value = "{id}/print", method = RequestMethod.GET)
    public Machine executePrint(@PathVariable("id") long id) {
        Machine machine = this.repository.findOne(id);
        machine.setQuantity(machine.getQuantity() - machine.getRate());
        if (machine.getQuantity() < 0) {
            machine.setQuantity(0);
        }
        this.repository.save(machine);
        return machine;
    }
}
