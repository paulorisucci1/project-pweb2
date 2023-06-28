package br.edu.ifpb.pweb2.pweb2.config;

public class Paths {
    public static final String INSTITUTIONS = "/institutions";

    public static final String INSTITUTIONS_ID = INSTITUTIONS+"/{idInstitution}";

    public static final String HOME = "/home";

    public static final String FORM = "/form";

    public static final String ACADEMIC_TERMS = "/academic_terms";

    public static final String STUDENTS = "/students";

    public static final String STUDENTS_ID = STUDENTS+"/{idStudent}";

    public static final String STUDENT_ENROLLMENTS = STUDENTS_ID+"/enrollments";

    public static final String ENROLLMENTS = "/enrollments";
}
