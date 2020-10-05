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

import com.zhoyq.server.jt808.entity.VehicleDeviceSimLink;
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
public interface VehicleDeviceSimLinkMapper extends BaseMapper<VehicleDeviceSimLink> {
    String TABLE_NAME = "JT808_LINK_VEHICLE_DEVICE_SIM";
    String EXIST_CONDITION = "and (thru_date is null or thru_date > CURRENT_TIMESTAMP)";
    String COLUMNS = "uuid, sim, license, device, auth, from_date as fromDate, thru_date as thruDate";

    /**
     * 通过 uuid 关闭卡号 车辆 和 设备之间的关联
     * @param uuid uuid
     * @return 关闭数量
     */
    @Update("update " + TABLE_NAME +
            " set thru_date=CURRENT_TIMESTAMP " +
            "where uuid = #{uuid} " + EXIST_CONDITION)
    int disable(String uuid);

    @Select("select " + COLUMNS +
            " from " + TABLE_NAME +
            " where sim=#{sim} " + EXIST_CONDITION + " order by from_date desc limit 0, 1")
    VehicleDeviceSimLink findBySim(String sim);

    @Select("select " + COLUMNS +
            " from " + TABLE_NAME +
            " where device=#{deviceId} " + EXIST_CONDITION + " order by from_date desc limit 0, 1")
    VehicleDeviceSimLink findByDevice(String deviceId);

    @Select("select " + COLUMNS +
            " from " + TABLE_NAME +
            " where license=#{license} " + EXIST_CONDITION + " order by from_date desc limit 0, 1")
    VehicleDeviceSimLink findByLicense(String license);
}
