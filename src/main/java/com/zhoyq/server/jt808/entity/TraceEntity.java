/*
 *  Copyright (c) 2020. 衷于栖 All rights reserved.
 *
 *  版权所有 衷于栖 并保留所有权利 2020。
 *  ============================================================================
 *  这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和
 *  使用。不允许对程序代码以任何形式任何目的的再发布。如果项目发布携带作者
 *  认可的特殊 LICENSE 则按照 LICENSE 执行，废除上面内容。请保留原作者信息。
 *  ============================================================================
 *  作者：衷于栖（feedback@zhoyq.com）
 *  博客：https://www.zhoyq.com
 *  创建时间：2020
 */

package com.zhoyq.server.jt808.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Zhoyq &lt;feedback@zhoyq.com&gt;
 * @date 2020-09-09
 */
@Table(
        name = "JT808_TRACE",
        schema = "PUBLIC"
)
@Entity
@Getter
@Setter
public class TraceEntity {
    @Id
    @Column
    private String uuid;

    @Column
    private String sim;

    // 标准数据

    @Column
    private Integer traceAlarm;
    @Column
    private Integer traceStatus;
    @Column
    private Double longitude;
    @Column
    private Double latitude;
    @Column
    private Integer height;
    @Column
    private Double speed;
    @Column
    private Integer direction;
    @Column
    private Long receiveTime;
    @Column
    private Long receiveServerTime;

    // 附加信息

    /**
     * 里程
     */
    @Column
    private Double mileage;
    /**
     * 油量
     */
    @Column
    private Double oilMass;
    /**
     * 行驶记录仪速度
     */
    @Column
    private Double recordSpeed;

    /**
     * 人工确认报警ID
     */
    @Column
    private Integer humanEnsureAlarmId;

    // 超速报警 附加数据

    @Column
    private Byte overSpeedPositionType;
    @Column
    private Integer overSpeedPositionId;

    // 进出区域/路线报警 附加数据

    @Column
    private Byte inOutPositionType;
    @Column
    private Integer inOutPositionId;
    @Column
    private Byte inOutDirection;

    // 路段行驶时间不足/过长报警 附加数据

    @Column
    private Integer driveTimePositionId;
    @Column
    private Integer driveTime;
    @Column
    private Byte driveTimeResult;

    // 扩展车辆状态位

    @Column
    private Integer traceStatusExt;
    @Column
    private Short ioStatus;

    /**
     * 胎压 hex Str
     */
    @Column
    private String tirePressure;

    /**
     * 车厢温度
     */
    @Column
    private Integer partTemp;

    /**
     * 模拟量 AD0
     */
    @Column
    private Integer ad0;

    /**
     * 模拟量 AD1
     */
    @Column
    private Integer ad1;

    /**
     * 信号强度
     */
    @Column
    private Byte signalStrength;

    /**
     * GNSS 定位卫星数量
     */
    @Column
    private Byte gnssNumber;

    // 自定义忽略
}

