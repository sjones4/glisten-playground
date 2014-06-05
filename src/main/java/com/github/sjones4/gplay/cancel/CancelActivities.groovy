package com.github.sjones4.gplay.cancel

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions

/**
 *
 */
@Activities(version = '1.0')
@ActivityRegistrationOptions(
    defaultTaskScheduleToStartTimeoutSeconds = -1L,
    defaultTaskStartToCloseTimeoutSeconds = 300L
)
interface CancelActivities {

  String echo( String message )
}
