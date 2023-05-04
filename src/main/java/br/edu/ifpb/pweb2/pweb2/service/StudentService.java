package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.model.Student;
import br.edu.ifpb.pweb2.pweb2.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;

    public Student create(Student newStudent) {

        verifyIfStudentRegistrationAlreadyExist(newStudent);

        return saveStudent(newStudent);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public Student update(Student updatedStudent) {
        final var foundStudent = searchById(updatedStudent.getIdStudent());

        foundStudent.update(updatedStudent);

        verifyIfStudentRegistrationAlreadyExist(foundStudent);

        return saveStudent(foundStudent);
    }

    public Student searchById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    void verifyIfStudentRegistrationAlreadyExist(Student student) {
        if(studentRepository.existsByNameAndIdStudentNot(student.getName(), student.getIdStudent())) {
            throw new EntityAlreadyExistException("The student already exist.");
        }
    }

    private Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void addEnrollmentOntoStudent(Enrollment enrollment, Integer idStudent) {
        final var student = searchById(idStudent);
        student.addEnrollment(enrollment);
        saveStudent(student);
    }

    public void removeEnrollmentFromStudent(Enrollment enrollment) {

    }
}
