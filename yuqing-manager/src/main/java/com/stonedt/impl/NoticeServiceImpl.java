package com.stonedt.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.NoticeMapper;
import com.stonedt.entity.Notice;
import com.stonedt.entity.UserEntity;
import com.stonedt.service.NoticeService;
import com.stonedt.util.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告管理业务层实现类
 * @author 文轩
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }
    @Override
    public PageInfo<Notice> getNoticeList(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Notice> noticeList = noticeMapper.getNoticeList();
        PageInfo<Notice> pageInfo = new PageInfo<>(noticeList);
        return pageInfo;
    }

    /**
     * 发布公告
     *
     * @param user
     * @param notice 公告实体类
     * @return
     */
    @Override
    public ResultUtil addNotice(UserEntity user, Notice notice) {
        notice.setUser_id(user.getId());
        try {
            noticeMapper.addNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500,"发布失败");
        }
        return ResultUtil.ok();
    }

    /**
     * 获取最新公告
     *
     * @return 最新公告
     */
    @Override
    public ResultUtil getNewNotice() {
        try {
            List<Notice> noticeList = noticeMapper.getLastNotice();
            return ResultUtil.ok(noticeList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500,"获取失败");
        }
    }

    /**
     * 下架公告
     *
     * @param id 公告id
     * @return 下架结果
     */
    @Override
    public ResultUtil deleteNotice(Integer id) {
        try {
            noticeMapper.softDeleteNotice(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500,"下架失败");
        }
        return ResultUtil.ok();
    }

    /**
     * 上架公告
     *
     * @param id 公告id
     * @return 上架结果
     */
    @Override
    public ResultUtil upNotice(Integer id) {
        try {
            noticeMapper.updateStatus(id,1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500,"上架失败");
        }
        return ResultUtil.ok();
    }
}
