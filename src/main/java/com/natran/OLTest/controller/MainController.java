package com.natran.OLTest.controller;

import com.natran.OLTest.beans.Item;
import com.natran.OLTest.repos.ItemRepos;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        itemRepos.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping(path = "rmItem/{id}")
    @ApiOperation(value = "Delete item by id",
            notes = "Delete item by specific id from database")
    public ResponseEntity<Item> deleteItem(@PathVariable("id") long id) {
        itemRepos.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "updateItem")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        itemRepos.save(item);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @GetMapping(path = "item/{id}")
    @ApiOperation(value = "Find Item by id",
            notes = "Provide an id to look up specific item from list of items",
            response = Item.class)
    public ResponseEntity<Optional<Item>> getItem(@PathVariable("id") long id) {
        return ResponseEntity.ok(itemRepos.findById(id));
    }

    @PutMapping(path = "withdraw/{id}+{amount}")
    @ApiOperation(value = "Change amount of item by id",
            notes = "Provide an id to look up specific item from list of items, if find item change amount if amount of item=> amount",
            response = Item.class)
    public ResponseEntity<Item> withdraw(@PathVariable("id") Long id, @PathVariable("amount") Integer amount) {
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
            return null;
        }
        return null;
    }

}
