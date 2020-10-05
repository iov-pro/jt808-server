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

package com.zhoyq.server.jt808.event;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhoyq.server.jt808.constant.TraceAlarm;
import com.zhoyq.server.jt808.constant.TraceConstant;
import com.zhoyq.server.jt808.constant.TraceStatus;
import com.zhoyq.server.jt808.entity.DeviceEntity;
import com.zhoyq.server.jt808.entity.DictionaryEntity;
import com.zhoyq.server.jt808.entity.DivisionEntity;
import com.zhoyq.server.jt808.entity.VehicleEntity;
import com.zhoyq.server.jt808.mapper.DeviceMapper;
import com.zhoyq.server.jt808.mapper.DictionaryMapper;
import com.zhoyq.server.jt808.mapper.DivisionMapper;
import com.zhoyq.server.jt808.mapper.VehicleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Zhoyq &lt;feedback@zhoyq.com&gt;
 * @date 2020-09-08
 */
@Slf4j
@Component
public class ApplicationAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        // 新增默认终端和车辆
        VehicleMapper vehicleMapper = context.getBean(VehicleMapper.class);
        DeviceMapper deviceMapper = context.getBean(DeviceMapper.class);
        DictionaryMapper dictionaryMapper = context.getBean(DictionaryMapper.class);
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        DivisionMapper divisionMapper = context.getBean(DivisionMapper.class);

        DictionaryEntity systemInit = dictionaryMapper.findByTypeAndKey("系统标识", "init");

        if (systemInit == null) {
            // 测试车辆
            var vehicleLicense = "测试00000";
            var deviceId = "0000000";
            VehicleEntity vehicleEntity = vehicleMapper.findByLicense(vehicleLicense);
            if (vehicleEntity == null) {
                vehicleEntity = new VehicleEntity();
                vehicleEntity.setUuid(UUID.randomUUID().toString());
                vehicleEntity.setLicense(vehicleLicense);
                vehicleEntity.setFromDate(new Date(System.currentTimeMillis()));
                vehicleEntity.setUpdateDate(new Date(System.currentTimeMillis()));
                vehicleMapper.insert(vehicleEntity);
            }

            DeviceEntity deviceEntity = deviceMapper.selectById(DeviceEntity.class, deviceId);
            if (deviceEntity == null) {
                deviceEntity = new DeviceEntity();
                deviceEntity.setDeviceId(deviceId);
                deviceEntity.setFromDate(new Date(System.currentTimeMillis()));
                deviceEntity.setUpdateDate(new Date(System.currentTimeMillis()));
                deviceMapper.insert(deviceEntity);
            }

            // 字典信息
            List<DictionaryEntity> dics = new ArrayList<>();
            for (TraceAlarm buf : TraceConstant.TRACE_ALARMS) {
                if (buf != null) {
                    dics.add(this.genDic("报警标识", buf.getKey(), buf.getValue()));
                }
            }

            for (TraceStatus buf : TraceConstant.TRACE_STATUS) {
                if (buf != null) {
                    dics.add(this.genDic("状态标识", buf.getKey(), buf.getValue()));
                }
            }

            for (TraceStatus buf : TraceConstant.TRACE_STATUS_EXT) {
                if (buf != null) {
                    dics.add(this.genDic("状态标识", buf.getKey(), buf.getValue()));
                }
            }

            for (TraceStatus buf : TraceConstant.TRACE_IO_STATUS) {
                if (buf != null) {
                    dics.add(this.genDic("状态标识", buf.getKey(), buf.getValue()));
                }
            }

            dics.add(this.genDic("系统标识", "init", "true"));

            for (DictionaryEntity d: dics ) {
                if (dictionaryMapper.findByTypeAndKey(d.getType(), d.getKey()) == null) {
                    dictionaryMapper.insert(d);
                }
            }

            // 地区信息
            try(
                    InputStream is = this.getClass().getResourceAsStream("/static/divisions_2020.json")
            ){
                JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, Integer.class, String.class);
                Map<Integer, String> map = objectMapper.readValue(is, javaType);
                map.forEach((k, v) -> {
                    DivisionEntity divisionEntity = divisionMapper.selectById(DivisionEntity.class, k);
                    if (divisionEntity == null) {
                        divisionEntity = new DivisionEntity();
                        divisionEntity.setId(k);
                        divisionEntity.setName(v);
                        divisionMapper.insert(divisionEntity);
                    }
                });
            } catch (IOException e) {
                log.warn(e.getMessage());
            }

            log.info("system init success !");
        } else {
            log.info("system already init !");
        }
    }

    private DictionaryEntity genDic(String type, String key, String value) {
        DictionaryEntity entity = new DictionaryEntity();
        entity.setUuid(UUID.randomUUID().toString());
        entity.setType(type);
        entity.setKey(key);
        entity.setValue(value);
        entity.setFromDate(new Date(System.currentTimeMillis()));
        return entity;
    }
}
