package com.Anonymous.smart_printing_system.repository;


import com.Anonymous.smart_printing_system.model.PrintingLog;
import com.Anonymous.smart_printing_system.model.eenum.PrintingLogEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrintingLogRepository extends JpaRepository<PrintingLog, Long>
{
    @Query("SELECT p " +
            "FROM PrintingLog p WHERE p.logStatus = :status")
    List<PrintingLog> findPrintingLogsByLogStatus(PrintingLogEnum status);


    @Query("SELECT p " +
            "FROM PrintingLog p " +
            "ORDER BY p.id DESC")
    Page<PrintingLog> findPrintingLogsByStudentId(Long studentId, Pageable pageable);


    Page<PrintingLog> findPrintingLogsByPrinterId(Long printerId, Pageable pageable);
}
