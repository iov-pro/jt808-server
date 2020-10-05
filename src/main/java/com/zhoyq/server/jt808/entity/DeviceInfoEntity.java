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

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备信息点播关联存储表
 * 用于映射设备中存储的点播信息
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/10/4
 */
@Table(
        name = "JT808_DEVICE_INFO",
        schema = "PUBLIC"
)
@Entity
@Getter
@Setter
public class DeviceInfoEntity {
    @Id
    @Column
    private String uuid;

    /**
     * 关联的设备ID
     */
    @Column
    private String deviceId;

    /**
     * 关联的信息ID
     */
    @Column
    private String infoUuid;

    /**
     * 终端中的对应信息类型
     */
    @Column
    private Byte infoType;

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
