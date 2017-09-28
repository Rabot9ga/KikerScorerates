package ru.rabot9ga.kikerScorerates.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rabot9ga.kikerScorerates.entity.MongoKickerTable;
import ru.rabot9ga.kikerScorerates.repositories.KickerTableRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kickerTable")
@Slf4j
public class KickerTableController {

    @Autowired
    private KickerTableRepository kickerTableRepository;


    @RequestMapping("/getAll")
    public List<MongoKickerTable> getAllKickerTables(){
        return kickerTableRepository.findAll();
    }

    @RequestMapping("/getByID")
    public MongoKickerTable getByID(@RequestParam(value = "id") String id) {
        return kickerTableRepository.findOne(id);
    }

    @RequestMapping("/getTablesByName")
    public List<MongoKickerTable> getKickerTableByName(@RequestBody List<String> names){
        return names.stream().map(s -> kickerTableRepository.findByName(s)).collect(Collectors.toList());
    }


    @RequestMapping(value = "/createTables", method = RequestMethod.PUT)
    public List<MongoKickerTable> createKickerTables(@RequestBody List<String> names){
        List<MongoKickerTable> kickerTableList = names.stream()
                .map(s -> MongoKickerTable.builder().name(s).build())
                .collect(Collectors.toList());
        return kickerTableRepository.save(kickerTableList);
    }
}
