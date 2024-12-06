package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.model.SemesterConfiguration;
import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.repository.SemesterConfigurationRepository;
import com.Anonymous.smart_printing_system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentSchedulerService {

    private final StudentRepository studentRepository;
    private final SemesterConfigurationRepository semesterConfigurationRepository;

    /**
     * Scheduled task to update `studentNumRemained` every 4 months.
     */
    //@Scheduled(cron = "0 0 0 1 */4 *") // Runs at midnight on the 1st day every 4th month
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void addDefaultPagesToStudents()
    {
        SemesterConfiguration semesterConfiguration = semesterConfigurationRepository.findById(1L).orElse(null);
        assert semesterConfiguration != null;
        Long defaultPages = semesterConfiguration.getDefaultPageNumber();

        List<Student> students = studentRepository.findAll();
        for (Student student : students)
        {
            Long updatedPages = student.getStudentNumRemained() + defaultPages;
            student.setStudentNumRemained(updatedPages);
        }
        studentRepository.saveAll(students);
        System.out.println("Updated students' remaining pages with default value.");
    }
}
