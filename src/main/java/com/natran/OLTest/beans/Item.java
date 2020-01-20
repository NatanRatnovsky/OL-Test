package com.natran.OLTest.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@ApiModel(description = "Details about the item")
public class Item {
    @Id
    @ApiModelProperty(notes = "The unique id of the item")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill a name")
    @Length(max = 128, message = "Name too long(maximum 128)")
    @ApiModelProperty(notes = "The name of item")
    private String name;

    @ApiModelProperty(notes = "The amount of items")
    private int amount;

    @NotBlank(message = "Please fill inventory code")
    @Length(max = 24)
    @ApiModelProperty(notes = "The unique code of item")
    private String inventoryCode;

    public Item() {
    }

    public Item(String name, int amount, String inventoryCode) {
        this.name = name;
        this.amount = amount;
        this.inventoryCode = inventoryCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", inventoryCode='" + inventoryCode + '\'' +
                '}';
    }
}
