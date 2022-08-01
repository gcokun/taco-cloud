package gcokun.tacocloud.controller;

import gcokun.tacocloud.repository.TacoRepository;
import gcokun.tacocloud.taco.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "htpp://tacocloud:8080")
public class TacoController {
    private TacoRepository tacoRepository;

    public TacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> getTacoById(@PathVariable("id") Long id) {
        Optional<Taco> tacoOptional = tacoRepository.findById(id);
        if (tacoOptional.isPresent()) {
            return new ResponseEntity<>(tacoOptional.get(), HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        //@RequestBody ann. ensures JSON is bound to a taco object
        return tacoRepository.save(taco);
    }

    @PutMapping(path = "/{tacoName}", consumes = "application/json")
    public Taco putTaco(@PathVariable("tacoName") String tacoName, @RequestBody Taco taco) {
        taco.setName(tacoName);
        return tacoRepository.save(taco);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public Taco patchTaco(@PathVariable("id") Long id, @RequestBody Taco patchTaco) {
        Taco taco = tacoRepository.findById(id).get();
        if (taco.getName() != null) {
            taco.setName(patchTaco.getName());
        }
        //etc...
        return tacoRepository.save(taco);
    }
}
