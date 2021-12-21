
**字典**

- [x] DictionaryEntity (String uuid; String dicKey; String dicValue; String dicLabel; Date fromDate; Date updateDate; Date thruDate;)
    

**地区**

- [x] DivisionEntity (String uuid; String name;)

**组织**

- [x] OrgEntity (String uuid; String parent; String name; Date fromDate; Date updateDate; Date thruDate;)

**车辆**

- [x] VehicleEntity ( uuid, license, provinceId, cityId, licenseColor, vin, orgId, fromDate, updateDate, thruDate )

**设备**

- [x] DeviceEntity ( uuid, deviceId, manufacturer, model, rsa, orgId, fromDate, updateDate, thruDate )

**车辆设备卡号的连接**

- [x] VehicleDeviceSimLink ( uuid, license, device, sim, auth, orgId, fromDate, updateDate, thruDate )

**车辆虚拟群组**

- [x] VehicleGroupEntity ( uuid, parent, title, vehicleUuid, userName, fromDate, updateDate, thruDate )

**用户与组织的连接**

- [x] UserOrgLink ( uuid, userName, orgId, fromDate, updateDate, thruDate )

**设备消息**

- [x] DeviceMsgEntity ( uuid, userName, vehicle, device, sim, msgId, stream, sendTime, sendData, replyId, replyTime, replyData, replyFlag)

**定位信息**

- [x] TraceEntity ( uuid, sim, traceAlarm, traceStatus, longitude, latitude, height, speed, direction, receiveTime, 
                    receiveServerTime, mileage, oilMass, recordSpeed, humanEnsureAlarmId, overSpeedPositionType, 
                    overSpeedPositionId, inOutPositionType, inOutPositionId, inOutDirection, driveTimePositionId,
                    driveTime, driveTimeResult, traceStatusExt, ioStatus, tirePressure, partTemp, ad0, ad1,
                    signalStrength, gnssNumber )

**报警信息**

- [x] TraceAlarmEntity ( uuid, sim, alarmKey, startTime, endTime )

## 以下暂时未用到

**事件上报**
- [x] EventEntity
- [x] EventReportEntity
- [x] DeviceEventEntity
**信息点播相关**
- [x] InfoEntity
- [x] InfoOrderEntity
- [x] DeviceInfoEntity
**电子运单相关**
- [x] ElectronicWaybillEntity
**驾驶员信息相关**
- [x] DriverInfoEntity
**CAN总线 多媒体 透传 压缩**
- [x] MediaEntity 多媒体信息（不含实体）
- [x] UploadDataEntity 包含CAN总线、多媒体实体、透传数据、压缩数据
