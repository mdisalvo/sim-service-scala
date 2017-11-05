package com.simservice

/**
  * @author Michael Di Salvo
  * michael.vincent.disalvo@gmail.com
  */
object SimFunc extends Enumeration {
  val Jaccard:     SimFunc.Value = Value("jaccard")
  val JaroWinkler: SimFunc.Value = Value("jarowinkler")
  val Cosine:      SimFunc.Value = Value("cosine")
  val MDJaccard:   SimFunc.Value = Value("mdjaccard")
}
