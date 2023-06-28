package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Institution;
import br.edu.ifpb.pweb2.pweb2.repository.InstitutionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;

    public Institution create(Institution newInstitution) {

        verifyIfInstitutionAcronymAlreadyExistForRegister(newInstitution);

        return saveInstitution(newInstitution);
    }

    public List<Institution> listInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution update(Institution updatedInstitution) {
        final var foundInstitution = searchById(updatedInstitution.getIdInstitution());

        foundInstitution.update(updatedInstitution);

        verifyIfInstitutionAcronymAlreadyExistForUpdate(foundInstitution);

        return saveInstitution(foundInstitution);
    }

    public Institution searchById(Integer id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found"));
    }

    @Transactional
    public void delete(Integer id) {
        final var institution = searchById(id);
        institutionRepository.delete(institution);
    }

    public void addAcademicTermOntoInstitution(AcademicTerm academicTerm, Integer idInstitution) {
        final var institution = searchById(idInstitution);
        institution.addAcademicTerm(academicTerm);
        update(institution);
    }

    public void removeAcademicTermFromInstitution(AcademicTerm academicTerm) {
        final var institution = searchById(academicTerm.getInstitution().getIdInstitution());
        institution.removeAcademicTerm(academicTerm);
    }

    void verifyIfInstitutionAcronymAlreadyExistForRegister(Institution institution) {
        if(institutionRepository.existsByAcronym(institution.getAcronym())) {
            throw new EntityAlreadyExistException("The institution acronym already exist.");
        }
    }

    void verifyIfInstitutionAcronymAlreadyExistForUpdate(Institution institution) {
        if(institutionRepository.existsByAcronymAndIdInstitutionNot(institution.getAcronym(), institution.getIdInstitution())) {
            throw new EntityAlreadyExistException("The institution acronym already exist.");
        }
    }

    private Institution saveInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

}
