# JavaSE 巩固 - 水平测试

做完后自己对照答案打分，每题1分，满分10分。

---

## 一、选择题（每题1分）

### 1. 以下哪个是封装的关键体现？

A. 使用 extends 关键字
B. 使用 private 隐藏属性，提供 getter/setter
C. 使用 interface 定义行为
D. 使用 abstract 定义抽象类

> 答案：B

### 2. ArrayList 和 LinkedList 哪个查询更快？

A. ArrayList
B. LinkedList
C. 一样快
D. 看情况

> 答案：A（ArrayList底层数组，按索引直接取）

### 3. 下面哪个属于 Collection 体系，且可以保证元素不重复？

A. ArrayList
B. LinkedList
C. HashSet
D. HashMap

> 答案：C（HashSet 属于 Collection 体系。HashMap 属于 Map 体系，它保证的是键不重复，不是元素不重复）

### 4. 接口里的方法默认是什么修饰符？

A. private
B. protected
C. public
D. 默认不写

> 答案：C（接口方法默认 public abstract）

### 5. String 比较内容应该用什么？

A. ==
B. equals()
C. compare()
D. =

> 答案：B

---

## 二、填空题（每题1分）

### 6. Java 面向对象的三大特性是：______、______、______

> 答案：封装、继承、多态

### 7. 用 final 修饰一个方法，表示这个方法______

> 答案：不能被重写

### 8. 以下代码输出什么？

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");
System.out.println(list.get(1));
```

> 答案：B

---

## 三、简答题（每题1分）

### 9. 接口和抽象类有什么区别？（写出2点即可）

> 参考答案：
> ① 接口用 interface，抽象类用 abstract class
> ② 一个类可以实现多个接口，但只能继承一个抽象类
> ③ 接口的方法都是抽象的（Java8+可以有default），抽象类可以有普通方法

### 10. 多态是什么？举个例子说明

> 参考答案：
> 多态就是父类引用指向子类对象，调用同一个方法得到不同的结果。
> 例：Animal a = new Dog();  a.makeSound();  → 输出"汪汪"
>     Animal a = new Cat();  a.makeSound();  → 输出"喵喵"

---

## 评分标准

| 分数 | 水平判断 | 建议 |
|------|---------|------|
| 9-10分 | ✅ JavaSE 很扎实 | 直接进 MySQL |
| 7-8分 | ⚠️ 大部分掌握 | 再看一遍笔记巩固，进 MySQL |
| 5-6分 | 🔧 基础薄弱 | 建议补 JavaSE 基础再往下学 |
| 0-4分 | ❌ 基础不够 | 从 JavaSE 零基础开始学 |
