package com.gjj.controllers;

import com.gjj.dto.ExecuteResult;
import com.gjj.models.Notice;
import com.gjj.services.NoticeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeServer server;

    @PostMapping("save")
    public ExecuteResult<?> save(@RequestBody Notice param) {
        return server.save(param);
    }

    @GetMapping("findAll")
    public @ResponseBody List<Notice> findAll() {
        return server.findAll();
    }

    @GetMapping("remove/{id}")
    public ExecuteResult<?> remove(@PathVariable String id) {
        return server.remove(id);
    }

}
