/*
 *  Copyright (c) 2020. 刘路 All rights reserved
 *  版权所有 刘路 并保留所有权利 2020.
 *  ============================================================================
 *  这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和
 *  使用。不允许对程序代码以任何形式任何目的的再发布。如果项目发布携带作者
 *  认可的特殊 LICENSE 则按照 LICENSE 执行，废除上面内容。请保留原作者信息。
 *  ============================================================================
 *  刘路（feedback@zhoyq.com）于 2020. 创建
 *  http://zhoyq.com
 */

package com.zhoyq.server.jt808.entity;

import com.zhoyq.server.jt808.starter.entity.DriverAlarmInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 驾驶员信息表
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/10/5
 */
@Table(
        name = "JT808_DRIVER_INFO",
        schema = "PUBLIC"
)
@Entity
@Getter
@Setter
public class DriverInfoEntity {
    @Id
    @Column
    private String uuid;
    @Column
    private String deviceId;
    @Column
    private String driverName;
    @Column
    private String idCardNumber;
    @Column
    private String certificateNumber;
    @Column
    private String certificatePublishAgentName;
    @Column
    private Date certificateLimitDate;
    @Column
    private Date datetime;

    /**
     * 是否拔出
     */
    @Column
    private Boolean pullOutCard;
    /**
     * 认证是否未通过
     */
    @Column
    private Boolean unAuthentication;
    /**
     * 是否被锁定
     */
    @Column
    private Boolean locked;
    /**
     * 是否被拔出
     */
    @Column
    private Boolean pullOut;
    /**
     * 是否校验失败
     */
    @Column
    private Boolean checkFailed;

    /**
     * 创建时间
     */
    @Column
    private Date fromDate;
    /**
     * 更新时间
     */
    @Column
    private Date updateDate;
    /**
     * 失效时间
     */
    @Column
    private Date thruDate;
}
