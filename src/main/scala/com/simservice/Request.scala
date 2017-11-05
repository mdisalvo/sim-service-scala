package com.simservice

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

case class Request(textItemA: String, textItemB: String)

object Request {
  implicit val requestFormat: RootJsonFormat[Request] = jsonFormat2(
    Request.apply
  )
}
