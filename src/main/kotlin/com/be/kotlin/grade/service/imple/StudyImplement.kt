package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.StudyMapper
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.model.Subject
import com.be.kotlin.grade.repository.*
import com.be.kotlin.grade.service.interf.StudyInterface
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.io.File
import java.util.*
import kotlin.collections.HashMap

@Service
class StudyImplement(
    private val studyRepository: StudyRepository,
    private val studyMapper: StudyMapper,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val classRepository: ClassRepository,
    private val userRepository: UserRepository
) : StudyInterface {
    override fun addStudyStudent(studyDTO: StudyDTO): Response {
        val newStudy = studyMapper.toStudy(studyDTO)

        val existingStudy = newStudy?.studyClass?.id?.let {
            newStudy.student.studentId.let { it1 ->
                newStudy.subject.id.let { it2 ->
                    studyRepository.findByStudentStudentIdAndSubjectIdAndStudyClassId(
                        it1,
                        it2,
                        it
                    )
                }
            }
        }

        if (existingStudy != null) {
            throw AppException(ErrorCode.STUDY_EXISTED)
        }

        val studentId = newStudy?.student?.studentId ?: throw AppException(ErrorCode.STUDENT_ID_INVALID)

        if (!studentRepository.findById(studentId).isPresent) {
            throw AppException(ErrorCode.STUDENT_NOT_FOUND)
        }

        val subjectId = newStudy.subject.id ?: throw AppException(ErrorCode.SUBJECT_ID_INVALID)
        if (!subjectRepository.existsById(subjectId)) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }

        val classId = newStudy.studyClass.id ?: throw AppException(ErrorCode.CLASS_ID_INVALID)
        if (!classRepository.existsById(classId)) {
            throw AppException(ErrorCode.CLASS_NOT_FOUND)
        }

        val userFaculty = newStudy.student.user.faculty
        val subjectFaculty = newStudy.subject.faculty
        val studentMajor = newStudy.student.major
        val subjectMajor = newStudy.subject.major

        if (userFaculty != subjectFaculty && studentMajor != subjectMajor) {
            throw AppException(ErrorCode.FACULTY_MAJOR_MISMATCH)
        }

        if (userFaculty != subjectFaculty) {
            throw AppException(ErrorCode.FACULTY_MISMATCH)
        }

        if (studentMajor != subjectMajor) {
            throw AppException(ErrorCode.MAJOR_MISMATCH)
        }

        studyRepository.save(newStudy)

        return Response(
            statusCode = 200,
            message = "Study added successfully",
            studyDTO = studyMapper.toStudyDTO(newStudy))
    }

    override fun deleteStudyStudent(studyIdD: Long): Response {
        if (!studyRepository.findById(studyIdD).isPresent)
            throw AppException(ErrorCode.STUDY_NOT_FOUND)

        val deletedStudy = studyRepository.findById(studyIdD).get()

        // Xóa Study
        studyRepository.deleteById(studyIdD)
        return Response(
            studyDTO = studyMapper.toStudyDTO(deletedStudy),
            statusCode = 200,
            message = "Study deleted successfully"
        )
    }

    override fun updateStudyStudent(study: StudyDTO): Response {

        val studyId: Long = study.id ?: throw AppException(ErrorCode.STUDY_ID_INVALID)

        if (!studyRepository.findById(studyId).isPresent){
            throw AppException(ErrorCode.STUDY_NOT_FOUND)
        }


        // Chuyển đổi từ DTO sang Entity và lưu vào cơ sở dữ liệu
        val updatedStudy = studyMapper.toStudy(study)

        val userFaculty = updatedStudy?.student?.user?.faculty
        val subjectFaculty = updatedStudy?.subject?.faculty
        val studentMajor = updatedStudy?.student?.major
        val subjectMajor = updatedStudy?.subject?.major

        if (userFaculty != subjectFaculty && studentMajor != subjectMajor) {
            throw AppException(ErrorCode.FACULTY_MAJOR_MISMATCH)
        }

        if (userFaculty != subjectFaculty) {
            throw AppException(ErrorCode.FACULTY_MISMATCH)
        }

        if (studentMajor != subjectMajor) {
            throw AppException(ErrorCode.MAJOR_MISMATCH)
        }
        if (updatedStudy != null) {
            studyRepository.save(updatedStudy)
        }

        return Response(
            studyDTO = study,
            statusCode = 200,
            message = "Study updated successfully"
        )
    }

    override fun getStudyById(studyIdD: Long): Response {
        val studyGot = studyRepository.findById(studyIdD)
            .orElseThrow { AppException(ErrorCode.STUDY_NOT_FOUND) }

        val studyDTO = studyMapper.toStudyDTO(studyGot)
        return Response(
            statusCode = 200,
            message = "Study found successfully",
            studyDTO = studyDTO
        )
    }

    fun generateCSV(username: String, studyList: List<StudyDTO>): File {
        val subjectMap = HashMap<String, Optional<Subject>>()
        val studentName = userRepository.findByUsername(username).get().name
        val studentId = studentRepository.findByUserUsername(username).studentId

        for (study in studyList) {
            val subject = subjectRepository.findById(study.subjectId)
            subjectMap[study.subjectId] = subject
        }

        // Title row for the CSV
        val title = "BẢNG ĐIỂM CỦA SINH VIÊN ${studentName.uppercase()} - MSSV ${studentId} HỌC KỲ ${studyList[0].semester}"

        // Content rows
        val header = listOf("STT", "MÃ MÔN HỌC", "TÊN MÔN HỌC", "SỐ TÍN CHỈ", "ĐIỂM THÀNH PHẦN", "ĐIỂM TRUNG BÌNH")
        val rows = studyList.mapIndexed { index, study ->
            val grades = study.gradeList.joinToString("\n") { grade ->
                "Điểm ${grade.weight}%: ${grade.score}"
            }
            listOf(
                index + 1, // STT
                study.subjectId, // MÃ MÔN HỌC
                subjectMap[study.subjectId]?.get()?.name ?: "N/A", // TÊN MÔN HỌC
                subjectMap[study.subjectId]?.get()?.credits ?: 0, // SỐ TÍN CHỈ
                grades, // ĐIỂM THÀNH PHẦN (multi-line)
                study.score // ĐIỂM TRUNG BÌNH
            )
        }

        val file = File("$studentName-$studentId-${studyList[0].semester}.csv")

        // Write the CSV file
        file.outputStream().bufferedWriter(Charsets.UTF_8).use { writer ->
            writer.write("\uFEFF") // Write UTF-8 BOM for Excel compatibility
            writer.write(title) // Write the title row
            writer.newLine()
            writer.write(header.joinToString(",")) // Write the header row
            writer.newLine()
            rows.forEach { row ->
                writer.write(row.joinToString(",") { cell ->
                    // Quote cells that contain commas or newlines
                    if (cell.toString().contains(",") || cell.toString().contains("\n")) {
                        "\"${cell.toString()}\""
                    } else {
                        cell.toString()
                    }
                })
                writer.newLine()
            }
        }

        return file
    }

    override fun getStudyByUsernameAndSemester(username: String, semester: Int): Response {
        if (studyRepository.findByStudentUserUsernameAndSemester(username, semester).isEmpty())
            return Response(
                statusCode = 404,
                message = "Study not found"
            )

        val studyList = studyRepository.findByStudentUserUsernameAndSemester(username, semester)
        val studyDTOList = studyList.map { studyMapper.toStudyDTO(it) }
        val file = generateCSV(username, studyDTOList)
        val resource = FileSystemResource(file)

        return Response(
            statusCode = 200,
            message = "Study record for semester $semester found successfully",
            listStudyDTO = studyDTOList,
            file = resource
        )
    }
}