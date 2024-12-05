package com.Anonymous.smart_printing_system.repository;

import com.Anonymous.smart_printing_system.model.PagePrice;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface PagePriceRepository extends JpaRepository<PagePrice, Long> {
    ArrayList<PagePrice> findAllByOrderByNumberOfPagesAsc();
}
