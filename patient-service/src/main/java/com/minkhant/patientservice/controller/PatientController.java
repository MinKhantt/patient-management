package com.minkhant.patientservice.controller;

import com.minkhant.patientservice.dto.PatientRequestDto;
import com.minkhant.patientservice.dto.PatientResponseDTO;
import com.minkhant.patientservice.dto.validators.CreatePatientValidationGroup;
import com.minkhant.patientservice.exception.EmailAlreadyExistException;
import com.minkhant.patientservice.service.IPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final IPatientService patientService;

    @GetMapping("")
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patientResponseDTOList = patientService.getAllPatients();
        return ResponseEntity.ok(patientResponseDTOList);
    }

    @PostMapping("")
    @Operation(summary = "Create Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDto patientRequestDto
    ) throws EmailAlreadyExistException {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDto);
        return ResponseEntity.status(201).body(patientResponseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Patient By Id")
    public  ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable UUID id) {
        PatientResponseDTO patientResponseDTO = patientService.getPatientById(id);
        return ResponseEntity.ok(patientResponseDTO);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update Patient By Id")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID id,
            @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto
    ) throws EmailAlreadyExistException {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient By Id")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
