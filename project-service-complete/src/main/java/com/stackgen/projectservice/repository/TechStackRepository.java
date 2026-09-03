package com.stackgen.projectservice.repository;

import com.stackgen.projectservice.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TechStackRepository extends JpaRepository<TechStack, UUID> {
}
