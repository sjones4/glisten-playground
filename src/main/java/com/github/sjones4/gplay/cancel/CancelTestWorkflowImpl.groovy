package com.github.sjones4.gplay.cancel

import com.amazonaws.services.simpleworkflow.flow.DecisionContextProvider
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl
import com.netflix.glisten.WorkflowOperations
import com.netflix.glisten.WorkflowOperator
import com.netflix.glisten.impl.swf.SwfWorkflowOperations

/**
 *
 */
class CancelTestWorkflowImpl implements CancelTestWorkflow, WorkflowOperator<CancelActivities> {

  @Delegate
  WorkflowOperations<CancelActivities> workflowOperations = SwfWorkflowOperations.of( CancelActivities )

  private DecisionContextProvider contextProvider = new DecisionContextProviderImpl( )

  @Override
  void start( String message ) {
    doTry {
      status 'Trying echo'
      promiseFor( activities.echo( 'abcdefg' ) )
    } withCatch { Throwable t ->
      status "Retrying echo after: ${t.message}"
      promiseFor( activities.echo( 'hijklmnop' ) )
    } withFinally { String result ->
      if ( contextProvider.decisionContext.workflowContext.cancelRequested ) {
        status 'Result: CANCELLED'
      } else {
        status "Result: ${result}"
      }
    }
  }
}
