package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.model.Student;
import br.edu.ifpb.pweb2.pweb2.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;

    public Student create(Student newStudent) {

        verifyIfStudentRegistrationAlreadyExistForRegister(newStudent);

        return saveStudent(newStudent);
    }

    @Transactional
    public Page<Student> listStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Transactional
    public Page<Student> listStudentsWithoutEnrollments(Pageable pageable) {
        return studentRepository.findAllByCurrentEnrollmentIsNull(pageable);
    }

    public Student update(Student updatedStudent) {
        final var foundStudent = searchById(updatedStudent.getIdStudent());

        foundStudent.update(updatedStudent);

        verifyIfStudentRegistrationAlreadyExistForUpdate(foundStudent);

        return saveStudent(foundStudent);
    }

    @Transactional
    public Student searchById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    @Transactional
    public void delete(Integer id) {
        final var student = searchById(id);
        student.removeAllEnrollments();
        studentRepository.delete(student);
    }

    void verifyIfStudentRegistrationAlreadyExistForRegister(Student student) {
        if(studentRepository.existsByRegistration(student.getRegistration())) {
            throw new EntityAlreadyExistException("The student registration already exist.");
        }
    }

    void verifyIfStudentRegistrationAlreadyExistForUpdate(Student student) {
        if(studentRepository.existsByRegistrationAndIdStudentNot(student.getRegistration(), student.getIdStudent())) {
            throw new EntityAlreadyExistException("The student registration already exist.");
        }
    }

    @Transactional
    public void addEnrollmentOntoStudent(Enrollment enrollment, Integer idStudent) {
        final var student = searchById(idStudent);
        student.addEnrollment(enrollment);
        saveStudent(student);
    }

    @Transactional
    public void removeEnrollmentFromStudent(Enrollment enrollment) {
        final var student = searchById(enrollment.getStudent().getIdStudent());
        student.removeEnrollment(enrollment);
        saveStudent(student);
    }

    public void changeStudentCurrentEnrollment(Integer idStudent, Enrollment newCurrentEnrollment) {
        final var student = searchById(idStudent);
        student.setCurrentEnrollment(newCurrentEnrollment);
        saveStudent(student);
    }

    private Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
}
