package com.zhoyq.server.jt808.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Zhoyq &lt;feedback@zhoyq.com&gt;
 * @date 2021-12-21
 */
@Table(
        name = "JT808_LINK_USER_ORG"
)
@Entity
@Getter
@Setter
public class UserOrgLink {
    @Id
    @Column
    private String uuid;
    @Column
    private String userName;
    @Column
    private String orgId;
    @Column
    private Date fromDate;
    @Column
    private Date updateDate;
    @Column
    private Date thruDate;
}
