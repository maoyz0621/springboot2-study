JavaBean规范中有一个特别的地方，如果属性名的第二个字母是大写的，那么该属性名直接用作 getter/setter 方法中 get/set 的后部分，也就是说首字母大小写不变，即手动生成getter和setter方法首字母p是小写的。由于lombok生成的getter和setter方法首字母是大写的，导致前端传值赋不上值。

解决方法：

1、不去使用首字母大写或第二个字母大写的参数。

2、手动生成getter和setter方法。

3、使用@JsonProperty注解。