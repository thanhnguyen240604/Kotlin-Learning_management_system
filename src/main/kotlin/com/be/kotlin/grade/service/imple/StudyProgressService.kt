package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.model.Study
import com.be.kotlin.grade.model.StudyProgress
import com.be.kotlin.grade.model.enums.CreditType
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.StudyProgressRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.repository.SubjectRelationRepository
import com.be.kotlin.grade.service.interf.IStudyProgress
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StudyProgressService (
    private val studyProgressRepository: StudyProgressRepository,
    private val studentRepository: StudentRepository,
    private val subjectRelationRepository: SubjectRelationRepository,
    private val studyRepository: StudyRepository,
) : IStudyProgress {
    override fun addStudyProgress(studentId: Long) {
        val existingStudent = studentRepository.findById(studentId).
                orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }

        val newStudyProgress = StudyProgress(
            student = existingStudent
        )
        studyProgressRepository.save(newStudyProgress)
    }

    override fun updateStudyProgress(study: Study) {
//        val studentId = study.student.studentId
//        val existingStudent = studentRepository.findById(studentId).
//            orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }
//
//        val studyProgress = studyProgressRepository.findByStudentId(studentId)
//
//        //Case pass the subject
//        if (study.score >= 4.0 && study.gradesList.all{it.score > 0}) {
//            val studyClass = study.studyClass
//            val subjectRelation = subjectRelationRepository.findBySubjectRelationId(studyClass.subject.id, studyClass.subject.faculty)
//
//            //CHUA LAM TRUONG HOP DA TON TAI RELATION DO
//            //CHUA LAM TRUONG HOP LAY CAC MON TU DO CAO NHAT
//            //Case elective subject
//            if (subjectRelation != null) {
//                if (subjectRelation.creditType == CreditType.ELECTIVE || (subjectRelation.creditType == CreditType.MAJOR_ELECTIVE && studyProgress.majorElectiveCredits > 15)) {
//                    study.isElective = true
//                    //Case not over 9 credits
//                    if (studyProgress.electiveCredits < 9)
//                        studyProgress.electiveCredits += studyClass.subject.credits
//
//                    else { //Case choose 3 of the highest elective credits
//                        val studies = studyRepository.getListElectiveStudy(studentId)
//                        val top3Studies = studies.sortedByDescending { it.score }.take(3)
//
//                        // So sánh x với từng phần tử trong 3 phần tử đầu tiên
//                        for ((index, study) in top3Studies.withIndex()) {
//                            if (x > study.score) {
//                                ${index + 1} có điểm ${study.score}"
//                            }
//                        }
//                        studies.sortedByDescending { it.score }.take(3)
//                    }
//                } else { //Case major subject
//                    //Calculate Major Grade
//                    val oldMajorGPA = studyProgress.majorGPA
//                    val totalOldMajorCredits = studyProgress.totalMajorCredits
//                    val newMajorGrade = study.score
//                    val newMajorCredits = studyClass.subject.credits
//
//                    //Calculate new Major GPA and total Major credits
//                    studyProgress.majorGPA =
//                        (oldMajorGPA * totalOldMajorCredits + newMajorGrade * newMajorCredits) / (totalOldMajorCredits + newMajorCredits)
//                    studyProgress.totalMajorCredits += newMajorCredits
//
//                    if (subjectRelation.creditType == CreditType.MAJOR_ELECTIVE) {
//                        studyProgress.majorElectiveCredits += newMajorCredits
//                    }
//                }
//            }
//
//            //Calculate general credits
//            val oldGeneralGPA = studyProgress.generalGPA
//            val totalOldGeneralCredits = studyProgress.totalGeneralCredits
//            val newGeneralGrade = study.score
//            val newGeneralCredits = studyClass.subject.credits
//
//            studyProgress.generalGPA =
//                (oldGeneralGPA * totalOldGeneralCredits + newGeneralGrade * newGeneralGrade) / (totalOldGeneralCredits + newGeneralCredits)
//            studyProgress.totalGeneralCredits += studyClass.subject.credits
//
//        }
        TODO()
    }
}