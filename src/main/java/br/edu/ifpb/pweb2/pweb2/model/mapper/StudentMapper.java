package br.edu.ifpb.pweb2.pweb2.model.mapper;

import br.edu.ifpb.pweb2.pweb2.model.dto.StudentDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentDTO studentDTO);

    StudentDTO toDTO(Student student);

    List<StudentDTO> toDTOList(List<Student> studentList);
}
