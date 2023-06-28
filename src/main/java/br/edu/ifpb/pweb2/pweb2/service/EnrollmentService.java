package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;

    private StudentService studentService;

    private AcademicTermService academicTermService;


    public List<Enrollment> list() {
        return enrollmentRepository.findAll();
    }

    @Transactional
    public Enrollment createEnrollmentForStudent(Enrollment enrollment, Integer idStudent) {
        final var createdEnrollment = saveEnrollment(enrollment);
        studentService.addEnrollmentOntoStudent(createdEnrollment, idStudent);
        return createdEnrollment;
    }

    @Transactional
    public Enrollment update(Enrollment updatedEnrollment) {
        final var foundEnrollment = searchById(updatedEnrollment.getIdEnrollment());
        foundEnrollment.update(updatedEnrollment);
        return saveEnrollment(foundEnrollment);
    }

    @Transactional
    public List<Enrollment> listEnrollmentsOfStudent(Integer idStudent) {
        final var institution = studentService.searchById(idStudent);
        return enrollmentRepository.findAllByStudent(institution);
    }

    @Transactional
    public Enrollment searchById(Integer id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));
    }

    @Transactional
    public void delete(Integer id) {
        final var enrollment = searchById(id);
        studentService.removeEnrollmentFromStudent(enrollment);
        enrollmentRepository.delete(enrollment);
    }

    public List<AcademicTerm> listAcademicTermsForStudent(Integer idStudent) {
        final var foundStudent = studentService.searchById(idStudent);
        return academicTermService.listAcademicTermsOfInstitution(foundStudent.getInstitution().getIdInstitution());
    }

    @Transactional
    public void changeStudentCurrentEnrollment(Integer idStudent, Integer idEnrollment) {
        final var foundEnrollment = searchById(idEnrollment);
        studentService.changeStudentCurrentEnrollment(idStudent, foundEnrollment);
    }

    private Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public List<Enrollment> listExpiredEnrollments() {
        return enrollmentRepository.getExpiredEnrollments();
    }

    @Transactional
    public List<Enrollment> listExpiringEnrollmentsInXDays(Integer days) {
        Date expiringDate = getExpiringDateFromNow(days);
        return enrollmentRepository.getEnrollmentsExpiringInDate(expiringDate);
    }

    private Date getExpiringDateFromNow(Integer days) {
        Calendar calendar = getConfiguredCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    private Calendar getConfiguredCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
