package com.simservice

import com.google.common.base.Charsets
import com.google.common.hash.Hashing
import org.apache.commons.text.similarity.{CosineSimilarity, JaccardSimilarity, JaroWinklerSimilarity}
import scala.collection.JavaConverters._

/**
 * Jaccard, JaroWinkler, and CosineSim are from apache.commons.text
 * For MDJaccard see: https://mdisalvo.github.io/simservice/
 * Multi-Set Jaccarding with minwise hashing, locally sensitive.
 *
 * @author Michael Di Salvo
 * michael.vincent.disalvo@gmail.com
 */
trait SimFuncSupport {

  def simFunc(request: Request, func: SimFunc.Value): Response =
    func match {
      case SimFunc.Jaccard     =>
        Response(
          new JaccardSimilarity().apply(request.textItemA, request.textItemB).doubleValue,
          hasher(request.textItemA),
          request.textItemA.getBytes.length,
          hasher(request.textItemB),
          request.textItemB.getBytes.length
        )
      case SimFunc.JaroWinkler =>
        Response(
          new JaroWinklerSimilarity().apply(request.textItemA, request.textItemB).doubleValue,
          hasher(request.textItemA),
          request.textItemA.getBytes.length,
          hasher(request.textItemB),
          request.textItemB.getBytes.length
        )
      case SimFunc.MDJaccard   =>
        Response(
          SimFunction.getSimilarity(
            new TokenizerChain().compute(request.textItemA).asInstanceOf[TokenizerResults],
            new TokenizerChain().compute(request.textItemB).asInstanceOf[TokenizerResults]
          ).asInstanceOf[java.lang.Number].doubleValue(),
          hasher(request.textItemA),
          request.textItemA.getBytes.length,
          hasher(request.textItemB),
          request.textItemB.getBytes.length
        )
      case SimFunc.Cosine      =>
        Response(
          new CosineSimilarity().cosineSimilarity(
            cosineTokenizer(request.textItemA), cosineTokenizer(request.textItemB)
          ),
          hasher(request.textItemA),
          request.textItemA.getBytes.length,
          hasher(request.textItemB),
          request.textItemB.getBytes.length
        )
      case default             =>
        throw new Exception(s"$func is not implemented.")
    }

  protected def hasher(text: String): String = Hashing.md5().newHasher().putString(text, Charsets.UTF_8).hash.toString

  protected def cosineTokenizer(text: String): java.util.Map[CharSequence, Integer] =
    new TokenizerChain()
      .compute(text)
      .asInstanceOf[TokenizerResults]
      .getSigs.get(TokenizerType.FirstLetterPairTokenizer).asScala
      .map(k => k._1.toString -> k._2)
      .asJava.asInstanceOf[java.util.Map[CharSequence, Integer]]

}
