package com.simservice

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

case class Response(
    result: Double,
    md5TextItemA: String,
    tokenCountTextItemA: Int,
    md5TextItemB: String,
    tokenCountTextItemB: Int
)

object Response {
  implicit val requestFormat: RootJsonFormat[Response] = jsonFormat5(
    Response.apply
  )
}
