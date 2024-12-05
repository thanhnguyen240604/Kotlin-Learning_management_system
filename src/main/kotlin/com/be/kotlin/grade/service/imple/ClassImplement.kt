package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.Class
import com.be.kotlin.grade.Student
import com.be.kotlin.grade.dto.StudentDTO.StudentResponseDto
import com.be.kotlin.grade.mapper.StudentMapper
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.service.interf.ClassInterface

class ClassImplement(private val studentMapper: StudentMapper,
    private val classRepository: ClassRepository,
    private  val studyRepository: StudyRepository
) : ClassInterface {
    override fun getHighestGradeStudent(classId: Long): MutableList<StudentResponseDto> {
        val myClass = classRepository.findById(classId).orElse(null)
        val studyList = studyRepository.findByStudyClass(myClass)
        var maxGrade : Float = 0F
        val res : MutableList<StudentResponseDto> = mutableListOf()
        for(i in studyList){
            if(i.score>=maxGrade){ maxGrade=i.score}
        }
        for(i in studyList){
            if(i.score==maxGrade){
                i.student?.let { studentMapper.toStudentResponseDto(it,maxGrade) }?.let { res.add(it) }
            }
        }
        return res
    }
}