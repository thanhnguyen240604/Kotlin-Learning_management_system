package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Study
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
    fun findByStudentStudentIdAndSubjectIdAndSemester(
        studentId: Long,
        subjectId: String,
        semester: Int
    ): Study?

    @Query("SELECT s.studyClass.id FROM Study s WHERE s.student.studentId = :studentId")
    fun findClassIdsByStudentId(@Param("studentId") studentId: Long): List<Long>

    fun findByStudentUserUsernameAndSemester(username: String, semester: Int, pageable: Pageable): Page<Study>
    fun findByStudentUserUsernameAndSemester(username: String, semester: Int): List<Study>
    fun findByStudyClass(studyClass : Class) : MutableList<Study>

    @Query(
        """
    SELECT COUNT(s)
    FROM Study s
    WHERE s.subject.id = :subjectId
      AND s.score >= :minScore
      AND s.score < :maxScore
      AND s.semester BETWEEN :startSemester AND :endSemester
    """
    )
    fun countByScoreRangeAndSubjectIdAndSemester(
        @Param("subjectId") subjectId: String,
        @Param("minScore") minScore: Float,
        @Param("maxScore") maxScore: Float,
        @Param("startSemester") startSemester: Int,
        @Param("endSemester") endSemester: Int
    ): Long


    @Query(
        """
        SELECT COUNT(s)
        FROM Study s
        WHERE s.subject.id = :subjectId
          AND s.score >= :minScore
          AND s.score < :maxScore
        """
    )
    fun countByScoreRangeAndSubjectId(
        @Param("subjectId") subjectId: String,
        @Param("minScore") minScore: Float,
        @Param("maxScore") maxScore: Float
    ): Long

    @Query("SELECT s.student.studentId FROM Study s WHERE s.studyClass.id = :classId")
    fun findStudentIdByClassId(@Param("classId") classId: Long): List<Long>

    @Query("SELECT s.id FROM Study s WHERE s.student.studentId = :studentId AND s.subject.id = :subjectId AND s.studyClass.id = :classId AND s.semester = :semester")
    fun findStudyIdByClassIdAndSubjectIdAndStudentIdAndSemester(
        @Param("classId") classId: Long,
        @Param("subjectId") subjectId: String,
        @Param("studentId") studentId: Long,
        @Param("semester") semester: Int
    ): Long?
}