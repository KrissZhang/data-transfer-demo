package com.self.datatransferclient.modules.test.impl;

import com.self.datatransferclient.modules.test.domain.TransferTest;
import com.self.datatransferclient.modules.test.repository.TransferTestRepository;
import com.self.datatransferclient.modules.test.service.TransferTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Test Service Impl
 */
@Service
@Scope("singleton")
public class TransferTestServiceImpl implements TransferTestService {

    @Autowired
    private TransferTestRepository repository;

    @Override
    public List<TransferTest> findAll() {
        return repository.findAll();
    }

}
