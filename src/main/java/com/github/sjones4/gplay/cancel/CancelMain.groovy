package com.github.sjones4.gplay.cancel

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker
import com.netflix.glisten.InterfaceBasedWorkflowClient
import com.netflix.glisten.WorkflowClientFactory
import com.netflix.glisten.WorkflowDescriptionTemplate
import com.netflix.glisten.WorkflowTags

/**
 *
 */
class CancelMain {

  static void main( String[] args ) {
    AmazonSimpleWorkflow simpleWorkflow = new AmazonSimpleWorkflowClient(
        new BasicAWSCredentials( 'AKIAISIUFAVUK5F5OMYA', 'DHKqbp5lqqTAFdochsrbLUxaxozpSRJueXTpMTmB' ) )
    String domain = 'sjones-cancel'
    String taskList = 'sjones-cancel-tasklist'

    // Create workers
    WorkflowWorker workflowWorker = new WorkflowWorker( simpleWorkflow, domain, taskList )
    workflowWorker.setRegisterDomain( true )
    workflowWorker.setDomainRetentionPeriodInDays( 1 )
    workflowWorker.setWorkflowImplementationTypes( [CancelTestWorkflowImpl] )
    workflowWorker.start( )
    ActivityWorker activityWorker = new ActivityWorker( simpleWorkflow, domain, taskList )
    activityWorker.setRegisterDomain( true )
    activityWorker.setDomainRetentionPeriodInDays( 1 )
    activityWorker.addActivitiesImplementations( [new CancelActivitiesImpl()] )
    activityWorker.start( )

    // Run workflow
    WorkflowClientFactory workflowClientFactory = new WorkflowClientFactory(simpleWorkflow, domain, taskList)

    WorkflowDescriptionTemplate workflowDescriptionTemplate = new CancelWorkflowDescriptionTemplate( );

    InterfaceBasedWorkflowClient<CancelTestWorkflow> client =
        workflowClientFactory.getNewWorkflowClient( CancelTestWorkflow, workflowDescriptionTemplate, new WorkflowTags( ) )

    client.asWorkflow().start( 'cancel' )
    sleep( 1000 )
    client.requestCancelWorkflowExecution( )
    sleep( 9000 )

    println client.asWorkflow().getLogHistory()?.join('\n')?:'<EMPTY>'
    println 'done'
  }

}
