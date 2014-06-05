package com.github.sjones4.gplay.cancel

import com.netflix.glisten.ActivityOperations
import com.netflix.glisten.impl.swf.SwfActivityOperations

import java.util.concurrent.CancellationException

/**
 *
 */
class CancelActivitiesImpl implements CancelActivities {

  @Delegate ActivityOperations activityOperations = new SwfActivityOperations()

  @Override
  String echo(final String message) {
    for ( int i=0; i<5; i++ ) {
      try {
        println "Echo in progress ${i}"
        recordHeartbeat( "Echo in progress ${i}" )
      } catch ( CancellationException e ) {
        println "Echo cancelled for: ${message}"
        throw e
      }
      sleep( 1000 )
    }

    println "Echoing message: ${message}"
    message.reverse( )
  }
}
