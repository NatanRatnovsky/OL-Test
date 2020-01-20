package com.natran.OLTest.repos;

import com.natran.OLTest.beans.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepos extends CrudRepository <Item, Long> {
}
