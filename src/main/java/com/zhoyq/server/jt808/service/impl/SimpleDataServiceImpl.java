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

package com.zhoyq.server.jt808.service.impl;

import com.zhoyq.server.jt808.constant.TraceConstant;
import com.zhoyq.server.jt808.entity.*;
import com.zhoyq.server.jt808.helper.DataHelper;
import com.zhoyq.server.jt808.mapper.*;
import com.zhoyq.server.jt808.service.RelationService;
import com.zhoyq.server.jt808.starter.config.Const;
import com.zhoyq.server.jt808.starter.dto.SimAuthDto;
import com.zhoyq.server.jt808.starter.entity.*;
import com.zhoyq.server.jt808.starter.helper.ByteArrHelper;
import com.zhoyq.server.jt808.starter.service.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Slf4j
@Component
@AllArgsConstructor
public class SimpleDataServiceImpl implements DataService {

    private RelationService relationService;
    private DeviceMapper deviceMapper;
    private DeviceMsgMapper deviceMsgMapper;
    private VehicleDeviceSimLinkMapper vehicleDeviceSimLinkMapper;
    private VehicleMapper vehicleMapper;
    private ByteArrHelper byteArrHelper;
    private DataHelper dataHelper;
    private TraceMapper traceMapper;
    private TraceAlarmMapper traceAlarmMapper;
    private EventReportMapper eventReportMapper;

    /**
     * 通过手机号获取设备ID
     */
    private String getDeviceId(String sim) throws Exception {
        String deviceId = relationService.findDeviceIdBySim(sim);
        if (deviceId == null) {
            throw new Exception("no deviceId bind with sim " + sim);
        }
        return deviceId;
    }

    /**
     *  设备必须存在 才能存储完成
     */
    @Override
    public void terminalRsa(String phone, byte[] e, byte[] n) {
        log.info("{}, rsa [{}, {}]", phone, byteArrHelper.toHexString(e), byteArrHelper.toHexString(n));
        try {
            // 获取当前连接的设备ID
            String deviceId = getDeviceId(phone);
            int count = deviceMapper.updateRsaById(deviceId, byteArrHelper.union(e, n));
            if (count == 1) {
                log.info("{}, rsa update success !", phone);
            } else if (count == 0) {
                log.info("{}, rsa update failed !", phone);
            } else {
                // count > 1
                log.info("{}, rsa update success ! but more then one device has been updated !", phone);
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    @Override
    public byte[] terminalRsa(String sim) {
        return new byte[0];
    }

    /**
     * 有下发指令 才有应答 否则剔除
     */
    @Override
    public void terminalAnswer(String phone, int platformStreamNumber, String platformCommandId, String msgId, byte[] msgBody) {
        log.info("{}, answer {}", phone, msgId);
        try {
            // 获取设备ID
            String deviceId = getDeviceId(phone);
            if (platformStreamNumber == -1) {
                // 如果等于 -1 代表回传没有流水号 则使用符合其他条件的 最新一条的流水号
                DeviceMsgEntity deviceMsgEntity = deviceMsgMapper.findFirst(deviceId, phone, platformCommandId);
                platformStreamNumber = deviceMsgEntity.getStream().intValue();
            }
            int count = deviceMsgMapper.updateAnswer(deviceId, phone, platformStreamNumber,
                    platformCommandId, msgId, msgBody);
            if (count == 1) {
                log.info("{}, answer update success !", phone);
            } else if (count == 0) {
                log.info("{}, answer update failed !", phone);
            } else {
                // count > 1
                log.info("{}, answer update success ! but more then one answer has been updated !", phone);
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    /**
     * 心跳不存储
     */
    @Override
    public void terminalHeartbeat(String phone) {
        log.info("{}, heartbeat", phone);
    }

    /**
     * 终端注销 需要通过手机号 取消 车辆和设备之间的联系
     * 取消终端已经鉴权的缓存
     */
    @Override
    public void terminalCancel(String phone) {
        log.info("{}, cancel", phone);

        try {
            VehicleDeviceSimLink link = vehicleDeviceSimLinkMapper.findBySim(phone);
            if (link != null) {
                // 删除终端与车辆之间的联系
                int count = vehicleDeviceSimLinkMapper.disable(link.getUuid());
                log.info("删除终端与车辆之间的联系数量" + count);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 车辆信息 和 设备信息 是注册信息之前注册到平台的
     * 注册需要查找到 平台的车辆和设备并通过手机号来连接
     *
     * 车 设备 和 卡号 是三个不同的对象
     * 车辆 Vechile （province city licenseColor license）
     * 设备 Device （manufacturer deviceType deviceId）
     * 卡号 Sim (phone)
     */
    @Override
    public String terminalRegister(String phone, int province, int city, String manufacturer, String deviceType,
                                   String deviceId, byte licenseColor, String registerLicense) {
        log.info("{}, register", phone);
        // 查询 是否存在车辆
        VehicleEntity vehicleEntity = vehicleMapper.findByLicense(registerLicense);
        // 查询 是否存在终端
        DeviceEntity deviceEntity = deviceMapper.selectById(DeviceEntity.class, deviceId);

        if (vehicleEntity == null) {
            // 没有车牌号 查询是否存在 车辆大架号
            vehicleEntity = vehicleMapper.findByVin(registerLicense);
            if (vehicleEntity == null) {
                // 数据库无此车辆
                return Const.TERMINAL_REG_NO_VEHICLE;
            }
        }

        if (deviceEntity == null) {
            // 数据库无此终端
            return Const.TERMINAL_REG_NO_TERMINAL;
        }

        // 查看注册信息
        if (vehicleDeviceSimLinkMapper.findByLicense(vehicleEntity.getLicense()) != null) {
            return Const.TERMINAL_REG_HAS_VEHICLE;
        }

        if (vehicleDeviceSimLinkMapper.findByDevice(deviceEntity.getDeviceId()) != null) {
            return Const.TERMINAL_REG_HAS_TERMINAL;
        }

        // 鉴权码 电话后七位
        String auth = phone.substring(phone.length() - 7);

        // 保存车辆信息
        vehicleEntity.setProvinceId(province);
        vehicleEntity.setCityId(city);
        vehicleEntity.setLicenseColor(licenseColor);
        if (licenseColor == 0) {
            vehicleEntity.setVin(registerLicense);
        }
        vehicleEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        vehicleMapper.update(vehicleEntity);
        // 保存终端信息
        deviceEntity.setManufacturer(manufacturer);
        deviceEntity.setModel(deviceType);
        deviceEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        deviceMapper.update(deviceEntity);
        // 保存连接信息
        var linker = new VehicleDeviceSimLink();
        linker.setUuid(UUID.randomUUID().toString());
        linker.setSim(phone);
        linker.setDevice(deviceEntity.getDeviceId());
        linker.setLicense(vehicleEntity.getLicense());
        linker.setUuid(UUID.randomUUID().toString());
        linker.setFromDate(new Date(System.currentTimeMillis()));
        linker.setThruDate(null);
        linker.setAuth(auth);
        int count = vehicleDeviceSimLinkMapper.insert(linker);

        if (count == 1) {
            // 注册成功 返回鉴权码 (鉴权信息也可以存入数据库)
            // 返回 电话后七位
            return auth;
        } else {
            return null;
        }
    }

    /**
     * 定位信息上传
     */
    @Override
    public void terminalLocation(String phone, LocationInfo locationInfo, Integer mediaId) {
        log.info("{}, location", phone);
        // 获取标准信息 报警信息 状态信息 以及 附加信息
        TraceEntity trace = new TraceEntity();
        trace.setUuid(UUID.randomUUID().toString());

        trace.setTraceAlarm(dataHelper.genTraceAlarm(locationInfo.getAlarmInfo()));
        trace.setTraceStatus(dataHelper.genTraceStatus(locationInfo.getStatusInfo()));

        trace.setSim(phone);
        trace.setLatitude(locationInfo.getLatitude());
        trace.setLongitude(locationInfo.getLongitude());
        trace.setHeight(locationInfo.getHeight());
        trace.setSpeed(locationInfo.getSpeed());
        trace.setDirection(locationInfo.getDirection());
        Long dateTime = dataHelper.formatTraceDatetime(locationInfo.getDatetime());
        trace.setReceiveTime(dateTime);
        trace.setReceiveServerTime(System.currentTimeMillis());

        // 附加信息
        List<LocationAttachInfo> attachInfo = locationInfo.getAttachInfo();

        for (LocationAttachInfo attach : attachInfo) {
            byte[] data = attach.getData();
            switch (attach.getId()) {
                case 0x01:
                    trace.setMileage((double)byteArrHelper.fourbyte2int(data) / 10D);
                    break;
                case 0x02:
                    trace.setOilMass((double)byteArrHelper.twobyte2int(data) / 10D);
                    break;
                case 0x03:
                    trace.setRecordSpeed((double)byteArrHelper.twobyte2int(data) / 10D);
                    break;
                case 0x04:
                    trace.setHumanEnsureAlarmId(byteArrHelper.twobyte2int(data));
                    break;
                case 0x05:
                    trace.setTirePressure(byteArrHelper.toHexString(data));
                    break;
                case 0x06:
                    trace.setPartTemp(dataHelper.formatTracePartTemp(data));
                    break;
                case 0x11:
                    if(data.length == 1){
                        trace.setOverSpeedPositionType((byte)0);
                    } else {
                        trace.setOverSpeedPositionType(data[0]);
                        trace.setOverSpeedPositionId(
                                byteArrHelper.fourbyte2int(new byte[]{data[1], data[2], data[3], data[4]}));
                    }
                    break;
                case 0x12:
                    trace.setInOutPositionType(data[0]);
                    trace.setInOutPositionId(
                            byteArrHelper.fourbyte2int(new byte[]{data[1], data[2], data[3], data[4]})
                    );
                    trace.setInOutDirection(data[5]);
                    break;
                case 0x13:
                    trace.setDriveTimePositionId(
                            byteArrHelper.fourbyte2int(new byte[]{data[0], data[1], data[2], data[3]})
                    );
                    trace.setDriveTime(
                            byteArrHelper.twobyte2int(new byte[]{data[4], data[5]})
                    );
                    trace.setDriveTimeResult(data[6]);
                    break;
                case 0x25:
                    trace.setTraceStatusExt(dataHelper.genTraceStatusExt(data));
                    break;
                case 0x2A:
                    trace.setIoStatus(dataHelper.genIoStatus(data));
                    break;
                case 0x2B:
                    trace.setAd0(byteArrHelper.twobyte2int(new byte[]{data[2], data[3]}));
                    trace.setAd1(byteArrHelper.twobyte2int(new byte[]{data[0], data[1]}));
                    break;
                case 0x30:
                    trace.setSignalStrength(data[0]);
                    break;
                case 0x31:
                    trace.setGnssNumber(data[0]);
                    break;
                default:
            }
        }
        // 保存
        traceMapper.insert(trace);

        // 如果是历史信息的定位回传，可能会出现问题，需要使用另外的逻辑存储
        // 这里记录 每个 手机号的上报时间 如果 时间低于当前时间 就不在记录报警信息
        Long lastAlarmTime = TraceConstant.LAST_ALARM_TIME.get(phone);

        if (lastAlarmTime != null && dateTime <= lastAlarmTime) {
            return;
        }

        // 还需要保存报警信息
        Integer lastAlarm = TraceConstant.LAST_ALARM.get(phone);
        Integer currentAlarm = trace.getTraceAlarm();

        if (lastAlarm != null) {
            // 存在上一次报警信息时候 根据 报警类别（持续还是点）进行存储
            // 点报警 开始时间和结束时间是一样的
            for (int i = 0; i < TraceConstant.TRACE_ALARMS.length; i++) {
                var alarmInfo = TraceConstant.TRACE_ALARMS[i];
                if (alarmInfo == null) {
                    continue;
                }

                if (alarmInfo.getKeep()) {
                    // 持续报警
                    int lastFlag = (lastAlarm >> i) & 0x01;
                    int currentFlag = (currentAlarm >> i) & 0x01;
                    if (lastFlag == 0 && currentFlag == 1) {
                        // 上次报警没有 这次有 表示新增
                        var alarm = new TraceAlarmEntity();
                        alarm.setUuid(UUID.randomUUID().toString());
                        alarm.setAlarmKey(alarmInfo.getKey());
                        alarm.setSim(phone);
                        alarm.setStartTime(new Date(trace.getReceiveTime()));
                        this.traceAlarmMapper.insert(alarm);
                    } else if (lastFlag == 1 && currentFlag == 0){
                        // 上次报警有 这次没有 表示结束
                        TraceAlarmEntity alarm = this.traceAlarmMapper.findLastBySimAndKey(phone, alarmInfo.getKey());
                        // 没有查询出来 或者 已经设置了结束时间 则不处理
                        if (alarm != null && alarm.getEndTime() == null) {
                            alarm.setEndTime(new Date(trace.getReceiveTime()));
                            this.traceAlarmMapper.update(alarm);
                        }
                    }
                    // 上次有 这次有 表示 持续 不用处理
                    // 上次没有 这次没有 表示没有 不用处理
                } else {
                    // 点报警 新增即可
                    var alarm = new TraceAlarmEntity();
                    alarm.setUuid(UUID.randomUUID().toString());
                    alarm.setAlarmKey(alarmInfo.getKey());
                    alarm.setSim(phone);
                    alarm.setStartTime(new Date(trace.getReceiveTime()));
                    alarm.setEndTime(new Date(trace.getReceiveTime()));
                    this.traceAlarmMapper.insert(alarm);
                }
            }
        }
        // 直接保存报警信息
        TraceConstant.LAST_ALARM.put(phone, currentAlarm);
        TraceConstant.LAST_ALARM_TIME.put(phone, dateTime);
    }


    /**
     * 事件上报
     * 事件上报需要先设置设备关联事件 才能查询 否则只能查到ID 这个在平台上注意
     */
    @Override
    public void eventReport(String phone, byte eventReportAnswerId) {
        log.info("{}, report", phone);
        VehicleDeviceSimLink link = vehicleDeviceSimLinkMapper.findBySim(phone);
        if (link != null) {
            String deviceId = link.getDevice();
            EventReportEntity entity = new EventReportEntity();
            entity.setUuid(UUID.randomUUID().toString());
            entity.setDeviceId(deviceId);
            entity.setEventId(eventReportAnswerId);
            entity.setFromDate(new Date(System.currentTimeMillis()));
            entity.setUpdateDate(new Date(System.currentTimeMillis()));
            entity.setThruDate(null);
            eventReportMapper.insert(entity);
        }
    }

    /**
     * 信息点播
     */
    @Override
    public void orderInfo(String phone, byte type) {
        log.info("{}, order info", phone);
        // TODO
    }

    /**
     * 信息点播取消
     */
    @Override
    public void cancelOrderInfo(String phone, byte type) {
        log.info("{}, cancel order info", phone);
        // TODO
    }

    /**
     * 电子运单上报
     */
    @Override
    public void eBill(String phone, byte[] data) {
        log.info("{}, bill", phone);
        // TODO
    }

    /**
     * 驾驶员信息上报
     */
    @Override
    public void driverInfo(String phone, DriverInfo driverInfo) {
        log.info("{}, driver info", phone);
        // TODO
    }

    /**
     * CAN总线数据上传
     */
    @Override
    public void canData(String phone, CanDataInfo canDataInfo) {
        log.info("{}, can", phone);
        // TODO
    }

    /**
     * 多媒体数据上传
     */
    @Override
    public void mediaInfo(String phone, MediaInfo mediaInfo) {
        log.info("{}, media info", phone);
        // TODO
    }

    /**
     * 存储多媒体实体信息
     */
    @Override
    public void mediaPackage(String phone, byte[] mediaData, Integer mediaId) {
        log.info("{}, media package", phone);
        // TODO
    }

    /**
     * 数据上行透传
     */
    @Override
    public void dataTransport(String phone, DataTransportInfo dataTransportInfo) {
        log.info("{}, data transport", phone);
        // TODO
    }

    /**
     * 数据压缩上报
     */
    @Override
    public void compressData(String phone, byte[] data) {
        log.info("{}, compress data", phone);
        // TODO
    }

    /**
     * 终端鉴权
     * 不需要操作 因为注册的时候已经配置了鉴权码 不保存也可以鉴权成功
     * 可以作为记录使用
     */
    @Override
    public void terminalAuth(String phone, String authId, String imei, String softVersion) { }

    /**
     * 设置鉴权信息
     * 程序启动时 通过此接口 获取所有已经鉴权的信息 保证终端正常鉴权
     */
    @Override
    public List<SimAuthDto> simAuth() {
        List<VehicleDeviceSimLink> list = vehicleDeviceSimLinkMapper.selectAll(VehicleDeviceSimLink.class);
        List<SimAuthDto> res = new ArrayList<>(list.size());
        for (VehicleDeviceSimLink link: list) {
            SimAuthDto simAuthDto = SimAuthDto.builder().build();
            simAuthDto.setSim(link.getSim());
            simAuthDto.setAuth(link.getAuth());
            res.add(simAuthDto);
        }
        return res;
    }
}
