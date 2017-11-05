package com.simservice

import org.apache.commons.text.similarity.{CosineSimilarity, JaccardSimilarity, JaroWinklerSimilarity}

/**
 * @author Michael Di Salvo
 * michael.vincent.disalvo@gmail.com
 */
class SimFuncSupportSpec extends BaseSpec {

  val textA = "The quick brown fox jumped over the lazy dog"
  val textB = "The quick brown fox jumped over the lazy dog yo"
  val request: Request = Request(textA, textB)
  val md5A: String = hasher(textA)
  val md5B: String = hasher(textB)
  val tokenCountA: Int = textA.getBytes.length
  val tokenCountB: Int = textB.getBytes.length

  "SimFuncSupport" should {

    "Return correct similarity for Jaccard func" in {
      val expected = Response(
        new JaccardSimilarity().apply(textA, textB).doubleValue(),
        md5A,
        tokenCountA,
        md5B,
        tokenCountB
      )
      val actual = simFunc(request, SimFunc.Jaccard)
      expected shouldEqual actual
    }

    "Return correct similarity for JaroWinkler func" in {
      val expected = Response(
        new JaroWinklerSimilarity().apply(textA, textB).doubleValue(),
        md5A,
        tokenCountA,
        md5B,
        tokenCountB
      )
      val actual = simFunc(request, SimFunc.JaroWinkler)
      expected shouldEqual actual
    }

    "Return correct similarity for MDJaccard func" in {
      val expected = Response(
        SimFunction.getSimilarity(
          new TokenizerChain().compute(textA).asInstanceOf[TokenizerResults],
          new TokenizerChain().compute(textB).asInstanceOf[TokenizerResults]
        ).asInstanceOf[java.lang.Number].doubleValue(),
        md5A,
        tokenCountA,
        md5B,
        tokenCountB
      )
      val actual = simFunc(request, SimFunc.MDJaccard)
      expected shouldEqual actual
    }

    "Return correct similarity for Cosine func" in {
      val expected = Response(
        new CosineSimilarity().cosineSimilarity(
          cosineTokenizer(textA), cosineTokenizer(textB)
        ),
        md5A,
        tokenCountA,
        md5B,
        tokenCountB
      )
      val actual = simFunc(request, SimFunc.Cosine)
      expected shouldEqual actual
    }

  }

}
