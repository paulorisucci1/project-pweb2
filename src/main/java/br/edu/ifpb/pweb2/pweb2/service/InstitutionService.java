package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Institution;
import br.edu.ifpb.pweb2.pweb2.repository.InstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;

    public Institution create(Institution newInstitution) {

        verifyIfInstitutionAcronymAlreadyExist(newInstitution);

        return saveInstitution(newInstitution);
    }

    public List<Institution> listInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution update(Institution updatedInstitution) {
        final var foundInstitution = searchById(updatedInstitution.getIdInstitution());

        foundInstitution.update(updatedInstitution);

        verifyIfInstitutionAcronymAlreadyExist(foundInstitution);

        return saveInstitution(foundInstitution);
    }

    public Institution searchById(Integer id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found"));
    }

    public void delete(Integer id) {
        institutionRepository.deleteById(id);
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

    void verifyIfInstitutionAcronymAlreadyExist(Institution institution) {
        if(institutionRepository.existsByAcronymAndIdInstitutionNot(institution.getAcronym(), institution.getIdInstitution())) {
            throw new EntityAlreadyExistException("The institution already exist.");
        }
    }

    private Institution saveInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

}
