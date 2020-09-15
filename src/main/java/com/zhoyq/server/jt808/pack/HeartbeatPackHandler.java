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

package com.zhoyq.server.jt808.pack;

import com.zhoyq.server.jt808.starter.core.Jt808Pack;
import com.zhoyq.server.jt808.starter.core.PackHandler;
import com.zhoyq.server.jt808.starter.helper.ResHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhoyq &lt;feedback@zhoyq.com&gt;
 * @date 2020-09-15
 */
@Slf4j
@Jt808Pack(msgId = 0x0002)
@AllArgsConstructor
public class HeartbeatPackHandler implements PackHandler {

    private ResHelper resHelper;

    @Override
    public byte[] handle(byte[] phoneNum, byte[] streamNum, byte[] msgId, byte[] msgBody) {
        log.info("new heartbeat.");
        return resHelper.getPlatAnswer(phoneNum, streamNum, msgId, (byte) 0x00);
    }
}
