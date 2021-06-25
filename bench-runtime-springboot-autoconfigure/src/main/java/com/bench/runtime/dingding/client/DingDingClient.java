package com.bench.runtime.dingding.client;

import com.bench.runtime.dingding.message.Message;

/**
 * @author by cold
 * @date 2020/08/01
 */
public interface DingDingClient {

    /**
     * 发送消息
     *
     * @param url     钉钉url
     * @param message 消息
     */
    void sendMessage(String url, Message message);
}
