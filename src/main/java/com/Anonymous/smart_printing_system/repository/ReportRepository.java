package com.Anonymous.smart_printing_system.repository;


import com.Anonymous.smart_printing_system.model.Report;
import com.Anonymous.smart_printing_system.model.eenum.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long>
{
    Page<Report> findReportsByReportType(ReportType reportType, Pageable pageable);
}
