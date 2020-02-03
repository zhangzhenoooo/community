package com.zhangz.community.service;

import com.zhangz.community.dto.NotificationDTO;
import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.enums.NotificationStatusEnum;
import com.zhangz.community.enums.NotificationTypeEnum;
import com.zhangz.community.exception.CustomizeErrorCode;
import com.zhangz.community.exception.CustomizeException;
import com.zhangz.community.mapper.NotificationExtMapper;
import com.zhangz.community.mapper.NotificationMapper;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.Notification;
import com.zhangz.community.model.NotificationExample;
import com.zhangz.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/2/2 21:49
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private NotificationExtMapper notificationExtMapperMapper;

    public PaginationDTo list(Long userId, Integer page, Integer size) {

        PaginationDTo<NotificationDTO> paginationDTo = new PaginationDTo();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
//                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);//获取未回复的评论总数

        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //若totalPage = 0,则会出现size为负数的情况
        if (totalPage == 0){
            totalPage = 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTo.setPagination(totalPage,page);

        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
//                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        if (notificationList.size() == 0){
            return  paginationDTo;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setOuterId(notification.getOuterid());
            notificationDTO.setOuterTitle(notification.getOuterTitle());
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        paginationDTo. setData(notificationDTOList);
        return  paginationDTo;
    }



    public Long unReadCount(Long id) {
        Long unReadCount  = notificationExtMapperMapper.getUnreadCount(id);
        return unReadCount;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification.getReceiver() != user.getId()){
            throw  new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        if (!Objects.equals(notification.getReceiver(),user.getId())){
            throw  new CustomizeException(CustomizeErrorCode.NOTIFY_NOT_FIND);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        //将通知更新为已读
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andIdEqualTo(id);
        notificationMapper.updateByExample(notification,notificationExample);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setOuterId(notification.getOuterid());//字段名称不匹配，所以需要自己写
        notificationDTO.setOuterTitle(notification.getOuterTitle());//字段名称不匹配，所以需要自己写
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
