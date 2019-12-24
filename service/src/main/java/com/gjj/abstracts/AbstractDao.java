package com.gjj.abstracts;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 抽象类
 * 
 * @author ptt
 *
 */
public abstract class AbstractDao {

	protected Pageable getFirstPageable() {
		Pageable pageable = new PageRequest(0, 1);
		return pageable;
	}

}
