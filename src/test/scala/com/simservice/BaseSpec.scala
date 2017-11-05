package com.simservice

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

/**
 * @author Michael Di Salvo
 * michael.vincent.disalvo@gmail.com
 */
trait BaseSpec
  extends AnyWordSpec
    with Matchers
    with SimFuncSupport
