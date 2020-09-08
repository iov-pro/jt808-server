/*
 *  Copyright (c) 2020. 衷于栖 All rights reserved
 *  版权所有 衷于栖 并保留所有权利 2020.
 *  ============================================================================
 *  这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和
 *  使用。不允许对程序代码以任何形式任何目的的再发布。如果项目发布携带作者
 *  认可的特殊 LICENSE 则按照 LICENSE 执行，废除上面内容。请保留原作者信息。
 *  ============================================================================
 *  衷于栖（feedback@zhoyq.com）于 2020. 创建
 *  https://www.zhoyq.com
 */

package com.zhoyq.server.jt808.mapper;

import com.zhoyq.server.jt808.entity.DeviceMsgEntity;
import com.zhoyq.server.jt808.helper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Mapper
@Repository
public interface DeviceMsgMapper extends BaseMapper<DeviceMsgEntity> {
    String TABLE_NAME = "JT808_DEVICE_MSG";

    /**
     * 更新消息
     * @param deviceId 设备ID
     * @param sim 电话号码
     * @param platformStreamNumber 对应平台流水号
     * @param platformCommandId 平台消息ID
     * @param msgId 终端应答ID
     * @param msgBody 终端消息体
     * @return 更新数量
     */
    @Update("update " + TABLE_NAME +
            " set reply_id = #{msgId}, reply_time = CURRENT_TIMESTAMP,reply_flag=1,reply_data=#{msgBody} " +
            "where device = #{deviceId} and sim = #{sim} " +
            "and stream = #{platformStreamNumber} and msg_id = #{platformCommandId}")
    int updateAnswer(String deviceId, String sim, Integer platformStreamNumber,
                     String platformCommandId, String msgId, byte[] msgBody);

    /**
     * 查询符合条件的第一条信息
     * @param deviceId 设备ID
     * @param sim 卡号
     * @param platformCommandId 平台消息ID
     * @return 消息实体
     */
    @Select("select uuid, user, vehicle, device, sim, msg_id as msgId, stream, send_time as sendTime, " +
            "send_data as sendData, reply_id as replyId, reply_time as replyTime, reply_data as replyData, " +
            "reply_flag as replyFlag from " + TABLE_NAME + " where device = #{deviceId} and sim = #{sim} " +
            "and msg_id = #{platformCommandId} order by send_time desc limit 0, 1")
    DeviceMsgEntity findFirst(String deviceId, String sim, String platformCommandId);
}
