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
        name = "JT808_ORG"
)
@Entity
@Getter
@Setter
public class OrgEntity {
    @Id
    @Column
    private String uuid;
    @Column
    private String parent;
    @Column
    private String name;
    @Column
    private Date fromDate;
    @Column
    private Date updateDate;
    @Column
    private Date thruDate;
}
