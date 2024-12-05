package com.Anonymous.smart_printing_system.repository;

import com.Anonymous.smart_printing_system.model.PageConfig;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageConfigRepository extends JpaRepository<PageConfig, Long> {
    PageConfig findByPageType(PageType pageType);
}
