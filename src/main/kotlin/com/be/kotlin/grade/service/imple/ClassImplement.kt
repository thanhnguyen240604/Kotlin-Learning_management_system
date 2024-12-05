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
    private  val studyRepository: StudyRepository) :ClassInterface {
    override fun getHighestGradeStudent(classId: Long): MutableList<StudentResponseDto> {
        var myClass = classRepository.findById(classId).orElse(null)
        var studyList = studyRepository.findByStudyClass(myClass)
        var maxGrade : Float = 0F
        var res : MutableList<StudentResponseDto> = mutableListOf()
        for(i in studyList){
            if(i.score>=maxGrade){ maxGrade=i.score}
        }
        for(i in studyList){
            if(i.score==maxGrade){
                res.add(studentMapper.toStudentResponseDto(i.student,maxGrade))
            }
        }
        return res
    }
}