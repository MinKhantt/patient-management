package com.minkhant.patientservice.service;

import com.minkhant.patientservice.dto.PatientRequestDto;
import com.minkhant.patientservice.dto.PatientResponseDTO;
import com.minkhant.patientservice.exception.EmailAlreadyExistException;
import com.minkhant.patientservice.exception.PatientNotFoundException;
import com.minkhant.patientservice.grpc.BillingServiceGrpcClient;
import com.minkhant.patientservice.kafka.KafkaProducer;
import com.minkhant.patientservice.mapper.PatientMapper;
import com.minkhant.patientservice.model.Patient;
import com.minkhant.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository
                .findAll()
                .stream()
                .map(PatientMapper::toPatientResponseDTO)
                .toList();
    }

    @Override
    public PatientResponseDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient with id: " + id + " not found")
        );

        return PatientMapper.toPatientResponseDTO(patient);
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDto patientRequestDto) throws EmailAlreadyExistException {
        if (patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistException(
                    "A patient with this email " + patientRequestDto.getEmail() + " already exist!"
            );
        }
        Patient newPatient = PatientMapper.toPatient(patientRequestDto);
        patientRepository.save(newPatient);

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail()
        );

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toPatientResponseDTO(newPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDto patientRequestDto) throws EmailAlreadyExistException {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient with this id " + id + " does not exist!")
        );

        if (patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)) {
            throw new EmailAlreadyExistException("A patient with this email " + patientRequestDto.getEmail() + " already exist!");
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        return PatientMapper.toPatientResponseDTO(patientRepository.save(patient));
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
