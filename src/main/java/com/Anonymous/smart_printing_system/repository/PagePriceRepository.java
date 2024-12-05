package com.Anonymous.smart_printing_system.repository;

import com.Anonymous.smart_printing_system.model.PagePrice;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageConfigRepository extends JpaRepository<PagePrice, Long> {
    PagePrice findByPageType(PageType pageType);
}
