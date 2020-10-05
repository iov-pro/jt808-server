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

package com.zhoyq.server.jt808.helper;

import com.zhoyq.server.jt808.starter.config.Const;
import com.zhoyq.server.jt808.starter.entity.AlarmInfo;
import com.zhoyq.server.jt808.starter.entity.StatusInfo;
import com.zhoyq.server.jt808.starter.helper.ByteArrHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhoyq &lt;feedback@zhoyq.com&gt;
 * @date 2020-09-09
 */
@Slf4j
@Component
@AllArgsConstructor
public class DataHelper {
    ByteArrHelper byteArrHelper;

    public Integer formatTracePartTemp(byte[] data){
        int rev = 1;
        if ((data[0] & Const.BIN_0X80) == Const.BIN_0X80) {
            rev = -1;
        }
        return rev * byteArrHelper.twobyte2int(new byte[]{(byte)(data[0] & 0x7f), data[1]});
    }


    public Long formatTraceDatetime(String datetime) {
        Date date = formatDatetime(datetime);
        return date == null ? 0L : date.getTime();
    }

    public Date formatDatetimeMs(String datetime) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-ssss");
        try {
            return sdf2.parse(sdf1.format(new Date()) + "-" + datetime);
        } catch (ParseException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public Date formatDatetime(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        try {
            return sdf.parse(datetime);
        } catch (ParseException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public Date formatDate(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(datetime);
        } catch (ParseException e) {
            log.warn(e.getMessage());
            return null;
        }
    }



    public Integer genTraceAlarm(AlarmInfo alarmInfo) {
        int buf = 0x00000000;

        buf = alarmInfo.isEmergencyAlarm() ? buf | 0x00000001 : buf;
        buf = alarmInfo.isOverSpeedAlarm() ? buf | 0x00000002 : buf;
        buf = alarmInfo.isFatigueDrivingAlarm() ? buf | 0x00000004 : buf;
        buf = alarmInfo.isDangerWarning() ? buf | 0x00000008 : buf;
        buf = alarmInfo.isGnssModuleFault() ? buf | 0x00000010 : buf;
        buf = alarmInfo.isGnssConnectFault() ? buf | 0x00000020 : buf;
        buf = alarmInfo.isGnssShortCircuit() ? buf | 0x00000040 : buf;
        buf = alarmInfo.isPowerUnderpressure() ? buf | 0x00000080 : buf;

        buf = alarmInfo.isPowerFault() ? buf | 0x00000100 : buf;
        buf = alarmInfo.isLcdFault() ? buf | 0x00000200 : buf;
        buf = alarmInfo.isTtsFault() ? buf | 0x00000400 : buf;
        buf = alarmInfo.isCameraFault() ? buf | 0x00000800 : buf;
        buf = alarmInfo.isIcModuleFault() ? buf | 0x00001000 : buf;
        buf = alarmInfo.isOverSpeedWarn() ? buf | 0x00002000 : buf;
        buf = alarmInfo.isFatigueDrivingWarn() ? buf | 0x00004000 : buf;
        buf = alarmInfo.isDriverAgainstRules() ? buf | 0x00008000 : buf;

        buf = alarmInfo.isTirePressureWarning() ? buf | 0x00010000 : buf;
        buf = alarmInfo.isRightTurnBlindArea() ? buf | 0x00020000 : buf;
        buf = alarmInfo.isCumulativeDrivingTimeout() ? buf | 0x00040000 : buf;
        buf = alarmInfo.isStopTimeout() ? buf | 0x00080000 : buf;
        buf = alarmInfo.isInArea() ? buf | 0x00100000 : buf;
        buf = alarmInfo.isOutLine() ? buf | 0x00200000 : buf;
        buf = alarmInfo.isDrivingTimeIncorrect() ? buf | 0x00400000 : buf;
        buf = alarmInfo.isRouteDeviation() ? buf | 0x00800000 : buf;

        buf = alarmInfo.isVssFault() ? buf | 0x01000000 : buf;
        buf = alarmInfo.isOilFault() ? buf | 0x02000000 : buf;
        buf = alarmInfo.isStolenVehicle() ? buf | 0x04000000 : buf;
        buf = alarmInfo.isIllegalIgnition() ? buf | 0x08000000 : buf;
        buf = alarmInfo.isIllegalDisplacement() ? buf | 0x10000000 : buf;
        buf = alarmInfo.isCollisionWarn() ? buf | 0x20000000 : buf;
        buf = alarmInfo.isRollOverWarn() ? buf | 0x40000000 : buf;
        buf = alarmInfo.isIllegalOpeningTheDoor() ? buf | 0x80000000 : buf;

        return buf;
    }

    public Integer genTraceStatus(StatusInfo statusInfo) {
        int buf = 0x00000000;

        buf = statusInfo.isAcc() ? buf | 0x00000001 : buf;
        buf = statusInfo.isPositioning() ? buf | 0x00000002 : buf;
        buf = statusInfo.isSouth() ? buf | 0x00000004 : buf;
        buf = statusInfo.isWest() ? buf | 0x00000008 : buf;
        buf = statusInfo.isSuspended() ? buf | 0x00000010 : buf;
        buf = statusInfo.isEncryption() ? buf | 0x00000020 : buf;
        buf = statusInfo.isBrakeSystemWarning() ? buf | 0x00000040 : buf;
        buf = statusInfo.isLaneDepartureWarning() ? buf | 0x00000080 : buf;

        buf = buf | (statusInfo.getCargo() << 2);
        buf = statusInfo.isOilBreak() ? buf | 0x00000400 : buf;
        buf = statusInfo.isCircuitBreak() ? buf | 0x00000800 : buf;
        buf = statusInfo.isLocking() ? buf | 0x00001000 : buf;
        buf = statusInfo.isOpening1() ? buf | 0x00002000 : buf;
        buf = statusInfo.isOpening2() ? buf | 0x00004000 : buf;
        buf = statusInfo.isOpening3() ? buf | 0x00008000 : buf;

        buf = statusInfo.isOpening4() ? buf | 0x00010000 : buf;
        buf = statusInfo.isOpening5() ? buf | 0x00020000 : buf;
        buf = statusInfo.isGps() ? buf | 0x00040000 : buf;
        buf = statusInfo.isBeidou() ? buf | 0x00080000 : buf;
        buf = statusInfo.isGlonass() ? buf | 0x00100000 : buf;
        buf = statusInfo.isGalileo() ? buf | 0x00200000 : buf;
        buf = statusInfo.isVehicleStatus() ? buf | 0x00400000 : buf;

        return buf;
    }

    public Integer genTraceStatusExt(byte[] data) {
        return byteArrHelper.fourbyte2int(data);
    }

    public Short genIoStatus(byte[] data) {
        return (short)byteArrHelper.twobyte2int(data);
    }
}
