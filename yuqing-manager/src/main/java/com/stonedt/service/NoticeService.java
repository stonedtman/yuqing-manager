package com.stonedt.service;

import com.github.pagehelper.PageInfo;
import com.stonedt.entity.Notice;
import com.stonedt.entity.UserEntity;
import com.stonedt.util.ResultUtil;

/**
 * @author 文轩
 * @description 公告管理业务层接口
 */
public interface NoticeService {
    PageInfo<Notice> getNoticeList(Integer pageNum);

    /**
     * 发布公告
     *
     * @param user
     * @param notice 公告实体类
     * @return
     */
    ResultUtil addNotice(UserEntity user,Notice notice);

    /**
     * 获取最新公告
     * @return 最新公告
     */
    ResultUtil getNewNotice();

    /**
     * 下架公告
     * @param id 公告id
     * @return 下架结果
     */
    ResultUtil deleteNotice(Integer id);

    /**
     * 上架公告
     * @param id 公告id
     * @return 上架结果
     */
    ResultUtil upNotice(Integer id);
}
