package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.model.Study

interface IStudyProgress {
    fun addStudyProgress(studentId: Long)
    fun updateStudyProgress(study: Study)
}