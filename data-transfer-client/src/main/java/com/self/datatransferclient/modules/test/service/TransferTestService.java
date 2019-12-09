package com.self.datatransferclient.modules.test.service;

import com.self.datatransferclient.modules.test.domain.TransferTest;

import java.util.List;

/**
 * Test Service
 */
public interface TransferTestService {

    /**
     * 获取全表数据
     */
    List<TransferTest> findAll();

}
