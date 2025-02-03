package com.UTFPR.server.service;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.Notice;
import com.UTFPR.server.repository.CategoryRepository;
import com.UTFPR.server.repository.NoticeRepository;
import jakarta.transaction.Transactional;

import java.util.List;

public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }


    public boolean isExistentNotice(Notice notice) {
        List<Notice> notices = noticeRepository.findNoticeById(notice.getId());
        return !notices.isEmpty();
    }

    @Transactional
    public void registerNotice(Notice notice) {
        noticeRepository.save(notice);
    }


    public Notice getNoticeById(int id) {
        List<Notice> notices = noticeRepository.findNoticeById((long) id);
        return notices.isEmpty() ? null : notices.get(0);
    }

    public List<Notice> getAllNotices() {
        List<Notice> notices = noticeRepository.listAllNotices();
        return notices.isEmpty() ? null : notices;
    }

    public List<Notice> getAllNoticesOfCategory(int categoryId) {
        List<Notice> notices = noticeRepository.listAllNoticesOfCategory(categoryId);
        return notices.isEmpty() ? null : notices;
    }

    @Transactional
    public void deleteNotice(Notice notice){
        noticeRepository.deleteNotice(notice);
    }

    @Transactional
    public void editNoticeById(int id, Notice noticeEdited){
        noticeRepository.editNoticeById((long)id, noticeEdited);
    }
}
