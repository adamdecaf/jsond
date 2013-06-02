package jsond
import jsonz._
import scala.Dynamic
import scala.language.dynamics
import scala.language.reflectiveCalls
import scala.language.implicitConversions

object Practice {
  case class JsKeyAccumulator(
    keys: Seq[String]
  ) {
    def ++(other: Seq[String]) = JsKeyAccumulator(this.keys ++ other)
    def ++(other: String) = JsKeyAccumulator(this.keys :+ other)
  }

  implicit def AccumulateFields(jso: JsObject) = new {
    def get() = KeyAccumulator(JsKeyAccumulator(List.empty))
  }

  implicit final class JsObjectProcesser(val jso: JsObject) extends AnyVal {
    def process[T](path: KeyAccumulator): Option[T] = process(path.keyAcc)

    def process(path: JsKeyAccumulator): Option[JsValue] = {
      path.keys.foldLeft(Option.empty[JsValue]) { (obj, key) => obj match {
        case Some(o: JsObject) => o.get(key)
        case _ => None
      }}
    }
  }

  implicit class KeyAccumulator(val keyAcc: JsKeyAccumulator = JsKeyAccumulator(List.empty)) extends Dynamic {
    def selectDynamic(name: String) = new KeyAccumulator(keyAcc ++ name)
  }

  implicit class JsObjectWrapper(val jso: JsObject) extends AnyVal {
    def getAllKeys: Seq[String] = {
      jso.fields.toMap.foldLeft(Seq.empty[String]) { case (acc, (key, obj)) => obj match {
        case o: JsObject => acc ++ o.getAllKeys :+ key
        case _ => acc :+ key
      }}
    }
  }

  def run() = {
    val jso = JsObject("foo" -> JsObject("bar" -> JsBoolean(true) :: Nil) :: Nil)
    val path = jso.get().foo.bar
    println("A" + jso.process(path))
  }
}
