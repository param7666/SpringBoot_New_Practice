package com.stackgen.projectservice.repository;

import com.stackgen.projectservice.entity.DataDictionaryUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DataDictionaryUploadRepository extends JpaRepository<DataDictionaryUpload, UUID> {
    Optional<DataDictionaryUpload> findByProjectId(UUID projectId);
}
