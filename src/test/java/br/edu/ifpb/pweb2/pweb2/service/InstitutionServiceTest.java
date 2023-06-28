//package br.edu.ifpb.pweb2.pweb2.service;
//
//import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
//import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
//import br.edu.ifpb.pweb2.pweb2.model.InstitutionFactory;
//import br.edu.ifpb.pweb2.pweb2.repository.InstitutionRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//public class InstitutionServiceTest {
//
//    @InjectMocks
//    private InstitutionService institutionService;
//
//    @Mock
//    private InstitutionRepository institutionRepository;
//
//    @Test
//    public void shouldCreateInstitutionSuccessfully() {
//        final var newInstitution = InstitutionFactory.createInstitutionWithoutViolations();
//
//        when(institutionRepository.save(any())).thenReturn(newInstitution);
//        final var createdInstitution = institutionService.create(newInstitution);
//
//        assertThat(newInstitution).isEqualTo(createdInstitution);
//    }
//
//    @Test
//    public void shouldListInstitutionsSuccessfully() {
//        final var institutionList = InstitutionFactory.createInstitutionsList();
//
//        when(institutionRepository.findAll()).thenReturn(institutionList);
//        final var listedInstituions = institutionService.listInstitutions();
//
//        assertThat(institutionList).isEqualTo(listedInstituions);
//    }
//
//    @Test
//    public void shouldUpdateInstitutionSuccessfully() {
//        final var previousInstitution = InstitutionFactory.createDummyInstitution();
//        final var updatedInstitution = InstitutionFactory.createInstitutionWithoutViolations();
//
//        when(institutionRepository.findById(any())).thenReturn(Optional.of(previousInstitution));
//        when(institutionRepository.existsByAcronymAndIdInstitutionNot(any(), any())).thenReturn(false);
//        when(institutionRepository.save(any())).thenReturn(previousInstitution);
//
//        final var result = institutionService.update(updatedInstitution);
//
//        assertThat(result).isEqualTo(updatedInstitution);
//    }
//
//    @Test
//    public void shouldFindInstitutionSuccessfully() {
//        final var searchedInstitution = InstitutionFactory.createInstitutionWithoutViolations();
//
//        when(institutionRepository.findById(any())).thenReturn(Optional.of(searchedInstitution));
//
//        assertThat(institutionService.searchById(searchedInstitution.getIdInstitution())).isEqualTo(searchedInstitution);
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenEntityNotFound() {
//        final var nonexistentInstitutionId = 0;
//
//        when(institutionRepository.findById(any())).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> institutionService.searchById(nonexistentInstitutionId))
//                .isInstanceOf(EntityNotFoundException.class);
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenInstitutionAcronymAlreadyExist() {
//        final var existentInstitution = InstitutionFactory.createInstitutionWithoutViolations();
//
//        when(institutionRepository.existsByAcronymAndIdInstitutionNot(any(), any())).thenReturn(true);
//
//        assertThatThrownBy(() -> institutionService.verifyIfInstitutionAcronymAlreadyExistForUpdate(existentInstitution))
//                .isInstanceOf(EntityAlreadyExistException.class);
//    }
//
//    @Test
//    public void shouldDeleteInstitutionSuccessfully() {
//        final var deletedInstitutionId = 1;
//
//        institutionService.delete(deletedInstitutionId);
//
//        verify(institutionRepository).deleteById(deletedInstitutionId);
//    }
//}
