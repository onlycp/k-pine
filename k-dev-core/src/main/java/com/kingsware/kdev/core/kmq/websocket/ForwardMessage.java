package com.kingsware.kdev.core.kmq.websocket;

import lombok.Data;

@Data
public class ForwardMessage {

    private String token;
    private WmMessage wmMessage;
}
