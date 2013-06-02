package jsond

object Test {

  // Reduced test case
  sealed trait JsValue
  case class JsBoolean(b: Boolean) extends JsValue
  case class JsObject(fields: Seq[(String, JsValue)]) extends JsValue

  implicit class JsObjectWrapper(val jso: JsObject) extends AnyVal {
    def getAllKeys: Seq[String] = {
      val map = jso.fields.toMap
      map.foldLeft(Seq.empty[String]) { case (acc, (key, obj)) => obj match {
        case o: JsObject => acc ++ o.getAllKeys :+ key
        case _ => acc :+ key
      }}
    }
  }

  def run = {
    val jso = JsObject("foo" -> JsObject("bar" -> JsBoolean(true) :: Nil) :: Nil)
    println(jso.getAllKeys)
  }

  // End reduced case
}
