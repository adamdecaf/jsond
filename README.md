# jsond

Dynamic "getters" of json strings and jsonz objects. This is really more of a nice syntax over that process, but useful for other libraries and the like.

## Examples

Get properties from a string

```scala
val str = """
{
    "foo": {
        "bar": false
    }
}
"""

> json(str).foo.bar.get[String]
false
```

Get properties from a jsonz object

```scala
val js = JsObject(
    "foo" -> JsObject(
        "bar" -> JsBoolean(true) ::
        Nil
    ) ::
    Nil
)

json(js).foo.bar.get[String]
```
