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

package com.zhoyq.server.jt808.mapper;

import com.zhoyq.server.jt808.entity.DeviceEntity;
import com.zhoyq.server.jt808.entity.DictionaryEntity;
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
public interface DictionaryMapper extends BaseMapper<DictionaryEntity> {
    String TABLE_NAME = "JT808_DICTIONARY";
    String EXIST_CONDITION = "and (thru_date is null or thru_date > CURRENT_TIMESTAMP)";
    String COLUMNS = "uuid, key, value, type, from_date as fromDate, update_date as updateDate, thru_date as thruDate";


    @Select("select " + COLUMNS +
            " from " + TABLE_NAME +
            " where type = #{type} and key = #{key} " + EXIST_CONDITION)
    DictionaryEntity findByTypeAndKey(String type, String key);

}
