package com.gjj.services;

import com.gjj.dto.ExecuteResult;
import com.gjj.models.Notice;
import com.gjj.repositories.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServer {

    @Autowired
    private NoticeRepository repository;

    public ExecuteResult<?> save(Notice param) {
        if (param == null) {
            return ExecuteResult.fail("参数为空");
        }
        param.addedDate= new Date();
        repository.save(param);
        return ExecuteResult.ok();
    }

    public @ResponseBody List<Notice> findAll() {
        return repository.findAll();
    }

    public ExecuteResult<?> remove(String id) {
        if (StringUtils.isEmpty(id)) {
            return ExecuteResult.fail("参数为空");
        }
        Notice entity = repository.findOne(id);
        if (entity != null) {
            repository.delete(id);;
        }
        return ExecuteResult.ok();
    }

}
