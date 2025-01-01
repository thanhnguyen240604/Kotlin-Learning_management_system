package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.reportDTO.ReportOfSubjectRequestDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studyDTO.GetStudyDTO
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.service.interf.IStudy
import org.springframework.core.io.FileSystemResource
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/study")
class StudyController (
    private val studyService: IStudy
){
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/add")
    fun addStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.addStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/addByExcel")
    fun addStudyStudent(@RequestParam("file") file: MultipartFile): ResponseEntity<Response> {
        val response = studyService.processExcel(file)
        return ResponseEntity.status(response.statusCode).body(response)
    }
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PutMapping("/update")
    fun updateStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.updateStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete/{id}")
    fun deleteStudyStudent(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studyService.deleteStudyStudent(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    fun getStudyById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studyService.getStudyById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //Get all study of a semester
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/result/{semester}")
    fun getStudiesByUsernameAndSemester
                (@RequestParam(defaultValue = "0") page : Int,
                 @RequestParam(defaultValue = "10") size : Int,
                 @PathVariable semester: Int) : ResponseEntity<Response> {
        val context = SecurityContextHolder.getContext()
        val username: String = context.authentication.name

        val pageable : Pageable = PageRequest.of(page, size)
        val response = studyService.getStudyByUsernameAndSemester(username, semester, pageable)

        return ResponseEntity.status(response.statusCode).body(response)
    }

    //Get all study of a semester by csv
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/result/get-csv/{semester}")
    fun getSemesterCSV(@PathVariable semester: Int) : ResponseEntity<FileSystemResource> {
        val context = SecurityContextHolder.getContext()
        val username: String = context.authentication.name

        val response = studyService.getStudyByUsernameAndSemesterCSV(username, semester)
        val resource = response.file

        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${resource?.filename}")
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource)
    }


    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/result")
    fun getStudyByUsernameAndSubjectIdAndSemester(@RequestBody getGrade: GetStudyDTO): ResponseEntity<Response> {
        // Gọi dịch vụ xử lý logic
        val response = studyService.getGradeBySubjectIdAndSemester(getGrade.subjectId, getGrade.semester)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //Generate report of a subject in a semester
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/generate-report")
    fun generateSubjectReport(@RequestBody report: ReportOfSubjectRequestDTO): ResponseEntity<Response> {
        val response = studyService.generateSubjectReport(report)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}

