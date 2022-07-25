package com.example.otpsender.util

import com.example.otpsender.data.entity.MessageEntity

class DataReceiveEvent {

    private var messageEntity: MessageEntity? = null
    private var eventTag: EventTagType = EventTagType.EVENT_NONE


    fun isTagMatchWith(eventTag: EventTagType): Boolean {
        return this.eventTag == eventTag
    }

    constructor(eventTag: EventTagType, messageEntity: MessageEntity) {
        this.eventTag= eventTag
        this.messageEntity = messageEntity
    }

    fun getNewMessage():MessageEntity?{
        return this.messageEntity
    }
}