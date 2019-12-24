package com.gjj.controllers;

import com.gjj.dto.ExecuteResult;
import com.gjj.models.Contract;
import com.gjj.services.ContractServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contract")
public class ContractController {

	@Autowired
	private ContractServer server;

	@PostMapping("save")
	public ExecuteResult<?> save(@RequestBody Contract param) {
		return server.save(param);
	}

	@PostMapping("findAll")
	public @ResponseBody List<Contract> findAll() {
		return server.findAll();
	}

	@GetMapping("remove/{id}")
	public ExecuteResult<?> remove(@PathVariable String id) {
		return server.remove(id);
	}

}
