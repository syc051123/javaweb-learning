# JavaSE 补全练习（异常+泛型+IO+反射+API+设计模式）

---

## 异常处理

### 1. 以下哪个是运行时异常？

A. IOException
B. SQLException
C. NullPointerException
D. FileNotFoundException

> **答案：C**
> 运行时异常：空指针、数组越界、类型转换、算术异常。编译不检查，运行才报错。
> 非运行时异常：IO、SQL、文件找不到。编译就必须处理。

### 2. finally 块什么时候执行？

A. 只有 try 没异常时执行
B. 只有 catch 捕获到异常时执行
C. 不管有没有异常都会执行
D. 有异常就不执行

> **答案：C**
> finally 的用途就是释放资源（关闭文件、关闭数据库连接），必须确保执行。

### 3. 以下代码输出什么？

```java
try {
    System.out.print("A");
    return;
} finally {
    System.out.print("B");
}
```

A. A
B. B
C. AB
D. BA

> **答案：C**
> 即使 try 里有 return，finally 也会执行。输出 AB。

### 4. throws 和 throw 的区别？

> throws：声明方法可能会抛出什么异常，甩锅给调用者。
> throw：手动抛出一个异常对象。

---

## 泛型

### 5. 泛型的主要作用是什么？

A. 提高代码运行速度
B. 编译时检查类型安全，避免强转
C. 让代码更短
D. 替代继承

> **答案：B**

### 6. `List<? extends Number>` 可以接收什么类型的集合？

A. 只能接收 List<Number>
B. 可以接收 List<Number> 和 List<Integer> 和 List<Double>
C. 可以接收任何 List
D. 不能接收任何类型

> **答案：B**
> ? extends Number 表示 Number 及其子类。

---

## IO流

### 7. 读取文本文件，最推荐用哪个类？

A. FileInputStream
B. FileReader
C. BufferedReader
D. File

> **答案：C**
> BufferedReader.readLine() 一次读一行，效率高，开发最常用。

### 8. 以下哪个流可以读写图片？

A. FileReader
B. FileWriter
C. FileInputStream
D. BufferedReader

> **答案：C**
> 图片是二进制文件，用字节流（InputStream/OutputStream）处理。字符流只能处理文本文件。

---

## 反射

### 9. 以下哪个不是获取 Class 对象的方式？

A. 类名.class
B. 对象.getClass()
C. Class.forName("类全名")
D. new Class()

> **答案：D**
> Class 构造方法是私有的，不能 new。

### 10. 反射访问私有属性需要调用哪个方法？

A. setPublic()
B. setAccessible(true)
C. setVisible(true)
D. setPrivate(false)

> **答案：B**
> setAccessible(true) 暴力破解，让 Java 的访问权限检查失效。

---

## 常用API

### 11. String 拼接大量字符串，用哪个效率最高？

A. String 直接用 + 拼接
B. StringBuilder
C. StringBuffer
D. B 和 C 都可以，StringBuilder 更快

> **答案：D**
> StringBuilder 最快（不加锁），StringBuffer 线程安全但慢一点。

---

## 设计模式

### 12. 单例模式的作用是什么？

A. 一个类可以创建多个对象
B. 一个类只能创建一个对象
C. 一个类不能被继承
D. 一个类不能被实例化

> **答案：B**
> 比如线程池、数据库连接池，一个就够了，不需要创建多个。

### 13. 工厂模式的好处是什么？

A. 创建对象的逻辑集中管理，调用者不用关心怎么创建
B. 让代码更复杂
C. 提高运行速度
D. 减少类数量

> **答案：A**
> 把 new 对象集中到工厂里，改一处就行，不用到处改。
