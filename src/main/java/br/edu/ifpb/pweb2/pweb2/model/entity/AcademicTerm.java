package br.edu.ifpb.pweb2.pweb2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(exclude = "institution")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"year", "semester", "institution_id"}))
public class AcademicTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAcademicTerm;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    public void update(AcademicTerm updatedAcademicTerm) {
        this.semester = updatedAcademicTerm.getSemester();
        this.year = updatedAcademicTerm.getYear();
        this.startDate = updatedAcademicTerm.getStartDate();
        this.endDate = updatedAcademicTerm.getEndDate();
    }

    @Override
    public String toString() {
        if(Objects.isNull(institution)) {
            return "AcademicTerm{" +
                    "idAcademicTerm=" + idAcademicTerm +
                    ", year=" + year +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", institution=" + null +
                    '}';
        }
        return "AcademicTerm{" +
                "idAcademicTerm=" + idAcademicTerm +
                ", year=" + year +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", institution=" + institution.getAcronym() +
                '}';
    }
}
