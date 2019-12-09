package com.self.datatransferclient.modules.test.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Test Domain
 */
@Data
@ToString
@Entity
@Table(name = "TRANSFER_TEST")
@EntityListeners(AuditingEntityListener.class)
public class TransferTest {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "key1", unique = true, nullable = true)
    private String key1;

    @Column(name = "value1", unique = false, nullable = true)
    private String value1;

}
