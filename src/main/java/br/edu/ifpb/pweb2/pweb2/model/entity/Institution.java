package br.edu.ifpb.pweb2.pweb2.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInstitution;

    @Column(
            length = 10,
            unique = true,
            nullable = false
    )
    private String acronym;

    @Column(
            length = 20,
            nullable = false
    )
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="academic_term_id")
    private List<AcademicTerm> academicTermList;

    @OneToOne(cascade = CascadeType.REMOVE)
    private AcademicTerm currentAcademicTerm;

    public void update(Institution updatedInstitution) {
        this.acronym = updatedInstitution.getAcronym();
        this.phone = updatedInstitution.getPhone();
        this.currentAcademicTerm = updatedInstitution.getCurrentAcademicTerm();
    }

    public void addAcademicTerm(AcademicTerm academicTerm) {
        academicTerm.setInstitution(this);
        this.academicTermList.add(academicTerm);
        this.currentAcademicTerm = academicTerm;
    }

    public void removeAcademicTerm(AcademicTerm academicTerm) {
        academicTerm.setInstitution(null);
        this.academicTermList.remove(academicTerm);
        if(isCurrentAcademicTerm(academicTerm)) {
            currentAcademicTerm = null;
        }
    }

    private boolean isCurrentAcademicTerm(AcademicTerm academicTerm) {
        return Objects.equals(academicTerm, this.currentAcademicTerm);
    }

    @Override
    public String toString() {
        return "Institution{" +
                "idInstitution=" + idInstitution +
                ", acronym='" + acronym + '\'' +
                ", phone='" + phone + '\'' +
                ", academicTermList=" + academicTermList +
                ", currentAcademicTerm=" + currentAcademicTerm +
                '}';
    }
}
