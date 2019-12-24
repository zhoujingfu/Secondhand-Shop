package com.gjj.services;

import com.gjj.dto.ExecuteResult;
import com.gjj.models.Contract;
import com.gjj.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class ContractServer {

	@Autowired
	private ContractRepository repository;

	public ExecuteResult<?> save(Contract param) {
		if (param == null) {
			return ExecuteResult.fail("参数为空");
		}
		repository.save(param);
		return ExecuteResult.ok();
	}

	public @ResponseBody List<Contract> findAll() {
		return repository.findAll();
	}
	
	public ExecuteResult<?> remove(String id) {
		if (StringUtils.isEmpty(id)) {
			return ExecuteResult.fail("参数为空");
		}
		Contract entity = repository.findOne(id);
		if (entity != null) {
			repository.delete(id);;
		}
		return ExecuteResult.ok();
	}

}
