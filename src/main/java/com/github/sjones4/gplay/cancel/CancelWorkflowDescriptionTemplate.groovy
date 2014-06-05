package com.github.sjones4.gplay.cancel

import com.netflix.glisten.WorkflowDescriptionTemplate

/**
 *
 */
class CancelWorkflowDescriptionTemplate extends WorkflowDescriptionTemplate implements CancelTestWorkflow {

  @Override
  void start( String message ) {
    description = "${message} echoing workflow"
  }
}
