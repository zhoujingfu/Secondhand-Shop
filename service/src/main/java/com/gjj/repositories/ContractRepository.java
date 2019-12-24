package com.gjj.repositories;

import com.gjj.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, String> {

}
