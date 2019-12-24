package com.gjj.repositories;

import com.gjj.models.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, String> {

}