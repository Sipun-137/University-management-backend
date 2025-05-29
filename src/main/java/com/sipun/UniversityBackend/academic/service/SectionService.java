package com.sipun.UniversityBackend.academic.service;


import com.sipun.UniversityBackend.academic.dto.SectionDto;
import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.repo.BatchRepo;
import com.sipun.UniversityBackend.academic.repo.BranchRepo;
import com.sipun.UniversityBackend.academic.repo.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepo repo;


    @Autowired
    private BatchRepo batchRepo;

    @Autowired
    private BranchRepo branchRepo;


    public Section save(SectionDto sectiondto) {

        Batch batch=batchRepo.findById(sectiondto.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Branch branch =branchRepo.findById(sectiondto.getBranchId())
                .orElseThrow(()->new RuntimeException("Branch not found"));

        Section section=Section.builder()
                .name(sectiondto.getName())
                .maxStrength(sectiondto.getMaxStrength())
                .branch(branch)
                .batch(batch)
                .build();

        System.out.println(section);
        return repo.save(section);
    }

    public List<Section> getAll() {
        return repo.findAll();
    }

    public Section getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Section not found"));
    }

    public Section update(Long id, SectionDto sectionDto) {
        Batch batch=batchRepo.findById(sectionDto.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Branch branch =branchRepo.findById(sectionDto.getBranchId())
                .orElseThrow(()->new RuntimeException("Branch not found"));


        Section existing = getById(id);

        Section section=Section.builder()
                .name(sectionDto.getName())
                .maxStrength(sectionDto.getMaxStrength())
                .branch(existing.getBranch())
                .batch(existing.getBatch())
                .build();
        existing.setName(section.getName());
        existing.setBatch(batch);
        existing.setBranch(branch);
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
