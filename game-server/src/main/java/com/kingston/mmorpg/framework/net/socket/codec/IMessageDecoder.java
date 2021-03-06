package com.kingston.mmorpg.framework.net.socket.codec;

import com.kingston.mmorpg.framework.net.socket.message.Message;

/**
 * 私有协议栈消息解码器
 *
 * @author kingston
 *
 */
public interface IMessageDecoder {

    /**
     * 	根据消息元信息反序列号为消息
     *  body已经是一个完整的消息包体，所以解码buff不需要复杂的操作，用NIO的ByteBuff即可
     *
     * @param cmd
     * @param body   完整的消息包体字节流
     * @return
     */
    Message readMessage(short cmd, byte[] body);

}