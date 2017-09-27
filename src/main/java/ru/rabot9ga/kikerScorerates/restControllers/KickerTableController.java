package ru.rabot9ga.kikerScorerates.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rabot9ga.kikerScorerates.entity.MongoKickerTable;
import ru.rabot9ga.kikerScorerates.repositories.KickerTableRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class KickerTableController {

    @Autowired
    private KickerTableRepository kickerTableRepository;


    @RequestMapping("/getAllKickerTables")
    public List<MongoKickerTable> getAllKickerTables(){
        return kickerTableRepository.findAll();
    }

    @RequestMapping("/getKickerTableByName")
    public MongoKickerTable getKickerTableByName(@RequestParam(value = "name") String name){
        return kickerTableRepository.findByName(name);
    }


    @RequestMapping(value = "/createKickerTable", method = RequestMethod.PUT)
    public MongoKickerTable createKickerTable(@RequestParam(value = "name") String name){
        return kickerTableRepository.save(MongoKickerTable.builder().name(name).build());
    }

    @RequestMapping(value = "/createKickerTables", method = RequestMethod.PUT)
    public List<MongoKickerTable> createKickerTables(@RequestBody List<String> names){
        List<MongoKickerTable> kickerTableList = names.stream()
                .map(s -> MongoKickerTable.builder().name(s).build())
                .collect(Collectors.toList());

        return kickerTableRepository.save(kickerTableList);
    }
}
