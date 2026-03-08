package com.minkhant.patientservice.service;

import com.minkhant.patientservice.dto.PatientRequestDto;
import com.minkhant.patientservice.dto.PatientResponseDTO;
import com.minkhant.patientservice.exception.EmailAlreadyExistException;

import java.util.List;
import java.util.UUID;

public interface IPatientService {
    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO getPatientById(UUID id);

    PatientResponseDTO createPatient(PatientRequestDto patientRequestDto) throws EmailAlreadyExistException;

    PatientResponseDTO updatePatient(UUID id, PatientRequestDto patientRequestDto) throws EmailAlreadyExistException;

    void deletePatient(UUID id);
}
