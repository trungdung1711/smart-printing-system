package com.Anonymous.smart_printing_system.repository;


import com.Anonymous.smart_printing_system.model.Printer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long>
{
    Page<Printer> findAllByCampusName(String campusName, Pageable pageable);

    Printer findPrinterById(Long id);
}
