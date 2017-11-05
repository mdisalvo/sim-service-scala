package com.simservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * A new take on my old simservice project.  Packaged that up as a jar on the classpath for computing sim on the
  * "mdJaccard" endpoint.  The remaining algorithms are from the apache commons text library.  Added them as I wanted
  * more than one endpoint for this demo service :).
  *
  * @author Michael Di Salvo
  * michael.vincent.disalvo@gmail.com
  */
object SimService extends App with SprayJsonSupport with SimFuncSupport {

  implicit val system: ActorSystem = ActorSystem("sim-service")
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  Http().bindAndHandle(route, "0.0.0.0", 8080)

  def route: Route =
    pathPrefix("jarowinklerSim") {
      pathEndOrSingleSlash {
        post {
          entity(as[Request]) { request =>
            onSuccess(Future(simFunc(request, SimFunc.JaroWinkler))) { response =>
              complete(OK, response)
            }
          }
        }
      }
    } ~
      pathPrefix("jaccardSim") {
        pathEndOrSingleSlash {
          post {
            entity(as[Request]) { request =>
              onSuccess(Future(simFunc(request, SimFunc.Jaccard))) { response =>
                complete(OK, response)
              }
            }
          }
        }
      } ~
      pathPrefix("mdJaccardSim") {
        pathEndOrSingleSlash {
          post {
            entity(as[Request]) { request =>
              onSuccess(Future(simFunc(request, SimFunc.MDJaccard))) { response =>
                complete(OK, response)
              }
            }
          }
        }
      } ~
      pathPrefix("cosineSim") {
        pathEndOrSingleSlash {
          post {
            entity(as[Request]) { request =>
              onSuccess(Future(simFunc(request, SimFunc.Cosine))) { response =>
                complete(OK, response)
              }
            }
          }
        }
      }
}
