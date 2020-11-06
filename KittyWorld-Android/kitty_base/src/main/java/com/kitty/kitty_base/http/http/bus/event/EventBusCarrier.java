package com.kitty.kitty_base.http.http.bus.event;


/**
 * 发送帐户名
 */

public class EventBusCarrier {
    private int eventType; //区分事件的类型
    private Object object;  //事件的实体类

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
