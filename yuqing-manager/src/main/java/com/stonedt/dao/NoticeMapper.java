package com.stonedt.dao;

import com.stonedt.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 文轩
* @description 针对表【notice】的数据库操作Mapper
* @createDate 2023-10-12 16:55:02
* @Entity com.stonedt.entity.Notice
*/
@Mapper
public interface NoticeMapper {

    /**
     * 公告列表
     * @return 公告列表
     */
    List<Notice> getNoticeList();

    /**
     * 发布公告
     * @param notice 公告实体类
     */
    void addNotice(Notice notice);

    /**
     * 获取最新公告
     * @return 最新公告
     */
    List<Notice> getLastNotice();

    /**
     * 下架公告
     * @param id 公告id
     */
    void softDeleteNotice(Integer id);

    /**
     * 上架公告
     *
     * @param id     公告id
     * @param status
     */
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}




