package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectRelationDTO

interface ISubjectRelation {
    fun addSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response
    fun updateSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response
    fun deleteSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response
}