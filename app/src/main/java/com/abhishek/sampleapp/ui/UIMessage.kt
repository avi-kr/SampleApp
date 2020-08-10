package com.abhishek.sampleapp.ui

/**
 * Created by Abhishek Kumar on 10/08/20.
 * (c)2020 VMock. All rights reserved.
 */
data class UIMessage(
    val message: String,
    val uiMessageType: UIMessageType
)

sealed class UIMessageType {

    class Toast : UIMessageType()

    class Dialog : UIMessageType()

    class AreYouSureDialog(
        val callback: AreYouSureCallback
    ) : UIMessageType()

    class None : UIMessageType()
}