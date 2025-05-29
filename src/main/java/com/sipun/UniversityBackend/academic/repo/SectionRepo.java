package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepo extends JpaRepository<Section,Long> {
    List<Section> findByBatchAndBranch(Batch batch, Branch branch);

    List<Section> findByBatchId(Long batchId);
    // SectionRepository.java

    @Query("""
        SELECT s FROM Section s
        WHERE s.branch.id = :branchId
          AND s.batch.startYear = :startYear
    """)
    List<Section> findByBranchIdAndBatchStartYear(
            @Param("branchId") Long branchId,
            @Param("startYear") int startYear
    );

}
