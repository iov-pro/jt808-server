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

package com.zhoyq.server.jt808.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zhoyq &lt;feedback@zhoyq.com&gt;
 * @date 2020-09-09
 */
public class TraceConstant {
    public static TraceAlarm[] TRACE_ALARMS = {
            new TraceAlarm("emergencyAlarm", "紧急报警",false),
            new TraceAlarm("overSpeedAlarm", "超速报警", true),
            new TraceAlarm("fatigueDrivingAlarm", "疲劳驾驶报警", true),
            new TraceAlarm("dangerWarning", "危险驾驶行为报警", true),
            new TraceAlarm("gnssModuleFault", "GNSS模块发生故障报警", true),
            new TraceAlarm("gnssConnectFault", "GNSS天线未接或被剪断报警", true),
            new TraceAlarm("gnssShortCircuit", "GNSS天线断路报警", true),
            new TraceAlarm("powerUnderpressure", "终端主电源欠压报警", true),
            new TraceAlarm("powerFault", "终端主电源掉电报警", true),
            new TraceAlarm("lcdFault", "终端LCD或显示器故障报警", true),
            new TraceAlarm("ttsFault", "TSS模块故障报警", true),
            new TraceAlarm("cameraFault", "摄像头故障报警", true),
            new TraceAlarm("icModuleFault", "道路运输证IC卡模块故障报警", true),
            new TraceAlarm("overSpeedWarn", "超速报警", true),
            new TraceAlarm("fatigueDrivingWarn", "疲劳驾驶预警", true),
            new TraceAlarm("driverAgainstRules", "违规行驶报警", true),
            new TraceAlarm("tirePressureWarning", "胎压预警", true),
            new TraceAlarm("rightTurnBlindArea", "右转盲区异常报警", true),
            new TraceAlarm("cumulativeDrivingTimeout", "当天累计驾驶超时报警", true),
            new TraceAlarm("stopTimeout", "超时停车报警", true),
            new TraceAlarm("inArea", "进出区域报警", false),
            new TraceAlarm("outLine", "进出路线报警", false),
            new TraceAlarm("drivingTimeIncorrect", "路段行驶时间不足/过长报警", false),
            new TraceAlarm("routeDeviation", "路线偏离报警", true),
            new TraceAlarm("vssFault", "车辆VSS故障", true),
            new TraceAlarm("oilFault", "车辆油量异常报警", true),
            new TraceAlarm("stolenVehicle", "车辆被盗报警（通过车辆防盗器）", true),
            new TraceAlarm("illegalIgnition", "车辆非法点火报警", false),
            new TraceAlarm("illegalDisplacement", "车辆非法位移报警", false),
            new TraceAlarm("collisionWarn", "碰撞检测报警", true),
            new TraceAlarm("rollOverWarn", "侧翻预警", true),
            new TraceAlarm("illegalOpeningTheDoor", "非法开门报警", false),
    };

    public static TraceStatus[] TRACE_STATUS = {
            new TraceStatus("acc", "ACC状态"),
            new TraceStatus("positioning", "定位状态"),
            new TraceStatus("south", "北纬南纬"),
            new TraceStatus("west", "东经西经"),
            new TraceStatus("suspended", "运营状态"),
            new TraceStatus("encryption", "经纬度加密状态"),
            new TraceStatus("brakeSystemWarning", "紧急刹车系统采集的前撞预警"),
            new TraceStatus("laneDepartureWarning", "车道偏移预警"),
            // 载货状态占两位
            new TraceStatus("cargo", "载货状态"),
            new TraceStatus("cargo", "载货状态"),
            new TraceStatus("oilBreak", "车辆油路状态"),
            new TraceStatus("circuitBreak", "车辆电路状态"),
            new TraceStatus("locking", "车门状态"),
            new TraceStatus("opening1", "门1（前门）状态"),
            new TraceStatus("opening2", "门2（中门）状态"),
            new TraceStatus("opening3", "门3（后门）状态"),
            new TraceStatus("opening4", "门4（驾驶席门）状态"),
            new TraceStatus("opening5", "门5（自定义）状态"),
            new TraceStatus("gps", "GPS卫星定位状态"),
            new TraceStatus("beidou", "北斗卫星定位状态"),
            new TraceStatus("glonass", "GLONASS卫星定位状态"),
            new TraceStatus("galileo", "Galileo卫星定位状态"),
            new TraceStatus("vehicleStatus", "车辆行驶状态"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    };

    public static TraceStatus[] TRACE_STATUS_EXT = {
            new TraceStatus("nearLight", "近光灯信号"),
            new TraceStatus("farLight", "远光灯信号"),
            new TraceStatus("rightTurnLight", "右转向灯信号"),
            new TraceStatus("leftTurnLight", "左转向灯信号"),
            new TraceStatus("stopSignal", "制动信号"),
            new TraceStatus("reverse", "倒挡信号"),
            new TraceStatus("fogLight", "雾灯信号"),
            new TraceStatus("positionLight", "示廓灯"),
            new TraceStatus("suonaSignal", "喇叭信号"),
            new TraceStatus("airCondition", "空调状态"),
            new TraceStatus("neutralSignal", "空挡信号"),
            new TraceStatus("retarderWork", "缓速器工作"),
            new TraceStatus("absWork", "ABS工作"),
            new TraceStatus("heatWork", "加热器工作"),
            new TraceStatus("clutchWork", "离合器状态"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
    };

    public static TraceStatus[] TRACE_IO_STATUS = {
            new TraceStatus("deepDormancy", "深度休眠状态"),
            new TraceStatus("dormancy", "休眠状态"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
    };

    public static Map<String, Integer> LAST_ALARM = new ConcurrentHashMap<>();
    public static Map<String, Long> LAST_ALARM_TIME = new ConcurrentHashMap<>();
}
