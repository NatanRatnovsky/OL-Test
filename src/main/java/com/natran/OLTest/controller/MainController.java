package com.natran.OLTest.controller;

import com.natran.OLTest.beans.Item;
import com.natran.OLTest.repos.ItemRepos;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class MainController {
    @Autowired
    private ItemRepos itemRepos;

    @GetMapping("/allItems")
    @ApiOperation(value = "Find all Item in database",
            notes = "Return list of all items from database")
    public ResponseEntity<Iterable<Item>> getAllItems() {
        return ResponseEntity.ok(itemRepos.findAll());
    }

    @PostMapping("/addItem")
    public ResponseEntity<Object> addItem(@Valid @RequestBody Item item) {
        if (item.getAmount() >= 0) {
            itemRepos.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        }
        return ResponseEntity.unprocessableEntity().body(item);
    }

    @DeleteMapping(path = "rmItem/{id}")
    @ApiOperation(value = "Delete item by id",
            notes = "Delete item by specific id from database")
    public Object deleteItem(@PathVariable("id") long id)
    throws ResourceNotFoundException {
        if (itemRepos.findById(id).isPresent()) {
            itemRepos.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK);
        }
            return ResponseEntity.notFound();
    }

    @PutMapping(path = "updateItem")
    public synchronized Object updateItem(@RequestBody Item item) {
        if (itemRepos.findById(item.getId()).isPresent()) {
            if (item.getAmount() >= 0) {
                itemRepos.save(item);
                return ResponseEntity.status(HttpStatus.OK).body(item);
            }
            return ResponseEntity.unprocessableEntity();
        }
        return ResponseEntity.notFound();
    }

    @GetMapping(path = "item/{id}")
    @ApiOperation(value = "Find Item by id",
            notes = "Provide an id to look up specific item from list of items",
            response = Item.class)
    public ResponseEntity<Optional<Item>> getItem(@PathVariable("id") long id) {
        if (id >= 0) {
            return Optional
                    .ofNullable(itemRepos.findById(id))
                    .map(item -> ResponseEntity.ok().body(item))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping(path = "withdraw/{id}")
    @ApiOperation(value = "Change amount of item by id",
            notes = "Provide an id to look up specific item from list of items, if item is found, change amount if amount of item=> amount")
    public synchronized Object withdraw(@PathVariable("id") Long id, @ModelAttribute(value = "amount") Integer amount) {
        if (id >= 0 && amount >= 0) {
            if (itemRepos.findById(id).isPresent()) {
                Item itemFromDB = itemRepos.findById(id).get();
                if (itemFromDB.getAmount() >= amount) {
                    itemFromDB.setAmount(itemFromDB.getAmount() - amount);
                    itemRepos.save(itemFromDB);
                    return ResponseEntity.status(HttpStatus.OK).body(itemFromDB);
                }
            }
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
