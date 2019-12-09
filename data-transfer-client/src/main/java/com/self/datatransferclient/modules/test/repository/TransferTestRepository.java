package com.self.datatransferclient.modules.test.repository;

import com.self.datatransferclient.modules.test.domain.TransferTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test Repository
 */
@Repository
@Transactional
public interface TransferTestRepository extends JpaRepository<TransferTest, Integer> {

}
