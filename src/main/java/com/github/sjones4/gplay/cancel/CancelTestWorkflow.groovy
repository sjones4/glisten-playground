package com.github.sjones4.gplay.cancel

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute
import com.amazonaws.services.simpleworkflow.flow.annotations.GetState
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions

/**
 *
 */
@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 60L)
interface CancelTestWorkflow {

  @Execute(version = '2.0')
  void start( String message )

  @GetState
  List<String> getLogHistory( )
}
