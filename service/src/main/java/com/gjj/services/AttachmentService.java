package com.gjj.services;

import com.gjj.models.Attachment;
import com.gjj.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gjj on 2018-03-09.
 */
@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    public void saveAttachment(Attachment attachment) {
        attachmentRepository.save(attachment);
    }

    public void saveAttachmentByParams(String attachmentName, String attachmentUrl, Integer goodId) {
        attachmentRepository.saveAttachmentByParams(attachmentName, attachmentUrl, goodId);
    }
}
