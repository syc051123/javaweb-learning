# JavaSE 巩固 - 完整水平测试（带解析）

满分100分，每题分值已标注。建议先自己做一遍再对答案。

---

## 一、选择题（每题3分，共30分）

---

### 1. 以下关于 `==` 和 `equals()` 的说法，正确的是？

A. `==` 比较的是内容，`equals()` 比较的是地址
B. `==` 比较的是地址，`equals()` 默认也是比较地址，但 String 重写了它
C. 两者完全一样
D. `==` 不能用于 String 比较

> **答案：B**
>
> **解析：**
> 想象你在食堂有两个餐盘，盘子里都打了红烧肉。
> - `==` 问的是：这两个是不是同一个餐盘？（比较内存地址）
> - `equals()` 问的是：这两个盘子里的菜一样不？（比较内容）
>
> 对于 `String` 类型，Java 帮我们重写了 `equals()`，让它比较内容而不是地址。
> 但对于 `==`，它永远比的是**地址**，不是内容。

---

### 2. 下面哪个不是 Java 面向对象的特性？

A. 封装
B. 继承
C. 多态
D. 重载

> **答案：D**
>
> **解析：**
> 面向对象三大特性是：封装、继承、多态。
> 重载（Overload）指的是同一个类中方法名相同、参数不同，它只是语法特性，不是面向对象的设计原则。
>
> 打个比方：三大特性是"设计房子的原则"，重载只是"砌砖的一种技术"。

---

### 3. `ArrayList` 和 `LinkedList` 的描述，哪个是对的？

A. ArrayList 增删快，LinkedList 查询快
B. ArrayList 查询快，LinkedList 增删快
C. 两者底层都是数组
D. 两者底层都是链表

> **答案：B**
>
> **解析：**
> - **ArrayList** 底层是**数组**，数据在内存里排成一排。你想找第3个人，直接去第3个位置拿就行，所以查得快。但你要在中间插一个人，后面所有人都要往后挪，所以增删慢。
> - **LinkedList** 底层是**双向链表**，像火车车厢一样，每节车厢连着一节。你想找第3节，得从第1节开始数过去，所以查得慢。但你要在中间加一节车厢，只需要改两节车厢的挂钩，所以增删快。
>
> 一句话：ArrayList 查得快、改得慢；LinkedList 改得快、查得慢。

---

### 4. 以下代码输出什么？

```java
String a = "hello";
String b = "hello";
System.out.println(a == b);
System.out.println(a.equals(b));
```

A. true true
B. true false
C. false true
D. false false

> **答案：A**
>
> **解析：**
> 这里有个小陷阱。Java 有一个"字符串常量池"。
> 当你写 `String a = "hello"` 时，Java 先在常量池里找有没有 "hello"，没有就创建一个。
> 写 `String b = "hello"` 时，发现常量池里已经有了，就把 b 指向同一个对象。
>
> 所以 a 和 b 指向的是**同一个对象**，== 比较地址，自然就是 true。
> equals() 比较内容，也都是 hello，所以也是 true。
>
> 但是注意！如果改成 `new String("hello")`，那就强制创建新对象了，== 结果会变成 false。

---

### 5. 以下关于 `HashSet` 的说法，错误的是？

A. 元素无序
B. 元素不可重复
C. 元素有序（按插入顺序）
D. 底层基于 HashMap

> **答案：C**
>
> **解析：**
> HashSet 的特点：元素无序、不可重复。
> 它的底层就是 HashMap，存数据时把元素作为 key 放进去，value 是个固定的常量对象。
> 因为 HashMap 的 key 是不可重复的，所以 HashSet 的元素也不可重复。
> 但 HashSet **不保证顺序**，你添加的顺序和取出的顺序可能不一样。

---

### 6. 下面哪个可以遍历 Map 的所有键？

A. map.values()
B. map.keySet()
C. map.entrySet()
D. B 和 C 都可以

> **答案：D**
>
> **解析：**
> - `map.keySet()`：返回所有键的集合，拿到键就能通过 `map.get(key)` 取值
> - `map.entrySet()`：返回所有键值对的集合，每个元素是 `Map.Entry`，可以同时拿到键和值
> - `map.values()`：只返回所有值，拿不到键
>
> 所以 keySet() 和 entrySet() 都能遍历所有键，但 entrySet() 效率更高（不用再 get 一次）。

---

### 7. 接口中的方法默认是？

A. private
B. protected
C. public
D. 不写修饰符就是默认权限

> **答案：C**
>
> **解析：**
> 接口里的方法，不管你写不写 `public`，编译后都是 `public abstract`。
>
> 你的代码写：
> ```java
> interface Flyable {
>     void fly();
> }
> ```
> 编译后等价于：
> ```java
> interface Flyable {
>     public abstract void fly();
> }
> ```
>
> 原因是接口的目的就是让别人实现的，所以方法必须是 public。

---

### 8. 以下关于 `final` 的说法，正确的是？

A. final 修饰的类可以被继承
B. final 修饰的方法可以被重写
C. final 修饰的变量值不能改
D. final 只能修饰变量

> **答案：C**
>
> **解析：**
> final 是"最终"的意思，也就是说：
> - final 修饰**变量** → 值定了就不能改（常量）
> - final 修饰**方法** → 方法定死了，子类不能重写
> - final 修饰**类** → 类定死了，不能被继承（比如 String 类就是 final 的）
>
> 不是只能修饰变量，三种都能修饰。

---

### 9. 下面哪个不是 Java8 的新特性？

A. Lambda 表达式
B. Stream 流
C. 接口的 default 方法
D. 泛型

> **答案：D**
>
> **解析：**
> Java 版本时间线：
> - Java 5（2004年）：泛型、枚举、增强 for 循环
> - Java 8（2014年）：Lambda、Stream、接口 default 方法、Optional、新日期 API
>
> 泛型比 Lambda 早了整整 10 年发布，不是 Java8 的特性。

---

### 10. 以下代码有什么问题？

```java
public abstract class Animal {
    public abstract void eat();
    public void sleep() {
        System.out.println("睡觉");
    }
}
public class Dog extends Animal {
    // 没有重写 eat 方法
}
```

A. 没有报错，一切正常
B. 编译报错，因为 Dog 没有实现抽象方法 eat()
C. 抽象类不能有普通方法
D. 抽象方法不能是 public

> **答案：B**
>
> **解析：**
> 抽象类可以同时有抽象方法和普通方法。
> 但是，继承抽象类的**非抽象子类**，必须实现父类的所有抽象方法。
> 这里 Dog 不是抽象类，却没有实现 eat()，编译会报错。
>
> 通俗理解：抽象类像一份"合同"，签了合同（继承）就必须履行合同里的条款（实现抽象方法）。

---

## 二、填空题（每空3分，共30分）

---

### 11. Java 面向对象的三大特性是：______、______、______

> **答案：封装、继承、多态**
>
> **解析：**
> 封装——把数据藏起来，通过方法暴露
> 继承——子类复用父类的代码
> 多态——同一个行为，不同表现

---

### 12. 以下代码输出______

```java
List<String> names = new ArrayList<>();
names.add("Tom");
names.add("Jerry");
names.add(0, "Alice");
System.out.println(names.get(0));
```

> **答案：Alice**
>
> **解析：**
> `add(0, "Alice")` 表示在索引 0 的位置插入 "Alice"，原来的 "Tom" 自动往后移到索引 1。
> 所以索引 0 现在是 "Alice"。
>
> 像排队插队一样：Alice 插到第一个位置，后面所有人都往后挪一位。

---

### 13. 以下代码输出______

```java
String s1 = new String("abc");
String s2 = new String("abc");
System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```

> **答案：false、true**
>
> **解析：**
> `new String("abc")` 强制在堆内存中创建新对象。
> s1 和 s2 是两个不同的对象，分别占不同的内存地址。
> 所以 `==` 比较地址 → false。
> 但 `equals()` 比较内容，两个字符串的内容都是 "abc" → true。
>
> 和前面的题目对比一下：
> - `String a = "abc"` → 常量池，相同内容只存一份
> - `new String("abc")` → 堆内存，每次都创建新对象

---

### 14. `final` 修饰变量表示______，修饰方法表示______，修饰类表示______

> **答案：值不能改、不能被重写、不能被继承**
>
> **解析：**
> 想象一份"最终版"文件：
> - 最终版的值（变量）——印出来了就不能改
> - 最终版的方法——子类不能修改这个方法
> - 最终的类——谁也不能继承这个类去扩展

---

### 15. Lambda 表达式的作用是______

> **答案：简化匿名内部类的写法**
>
> **解析：**
> 以前写按钮点击事件：
> ```java
> button.addActionListener(new ActionListener() {
>     @Override
>     public void actionPerformed(ActionEvent e) {
>         System.out.println("点击了");
>     }
> });
> ```
>
> 用 Lambda 一行搞定：
> ```java
> button.addActionListener(e -> System.out.println("点击了"));
> ```
>
> Lambda 只留下了最核心的东西：参数 → 做什么。

---

## 三、代码分析题（每题5分，共20分）

---

### 16. 这段代码输出什么？

```java
public class Test {
    public static void main(String[] args) {
        String a = "hello";
        String b = a.toUpperCase();
        System.out.println(a);
        System.out.println(b);
    }
}
```

> **答案：**
> hello
> HELLO
>
> **解析：**
> 关键点：**String 是不可变的**。
>
> `a.toUpperCase()` 并不会修改 a 本身，而是创建了一个**新的字符串对象** "HELLO" 并返回。
> 所以：
> - a 还是 "hello"
> - b 是刚创建的 "HELLO"
>
> 如果不理解：想象你在纸上写了 "hello"，然后用机器复印了一份大写的 "HELLO"。
> 原来的那张纸上还是 "hello"，新的那张是 "HELLO"。

---

### 17. 这段代码有什么问题？怎么修复？

```java
public class Person {
    private String name;
    private int age;
    public void printInfo() {
        System.out.println("姓名：" + name + "，年龄：" + age);
    }
}

public class Test {
    public static void main(String[] args) {
        Person p = new Person();
        p.name = "张三";  // 编译报错
    }
}
```

> **答案：** name 是 private 属性，外部不能直接访问。
>
> **修复方案：**
>
> **方案一（推荐）：添加 setter 方法**
> ```java
> public void setName(String name) {
>     this.name = name;
> }
> // 然后调用 p.setName("张三");
> ```
>
> **方案二（不推荐）：把 private 改成 public**
> ```java
> public String name;
> ```
>
> **解析：**
> private 的意思是"私有的"，只有自己类内部能访问。
> Test 类在外面，硬要直接访问 Person 的私有属性，就像你跑到别人家翻人家的抽屉。
>
> 正确的做法是通过 setter 方法，相当于按门铃让主人帮你拿东西——这就是封装的精髓。

---

### 18. 这段代码输出什么？

```java
public class Animal {
    public void sound() {
        System.out.println("动物叫");
    }
}
public class Dog extends Animal {
    public void sound() {
        System.out.println("汪汪");
    }
}
public class Test {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.sound();
    }
}
```

> **答案：汪汪**
>
> **解析：**
> 这就是多态的核心表现：
> 变量 a 的**编译时类型**是 Animal（左边），但**运行时类型**是 Dog（右边）。
>
> 调用 `a.sound()` 时，Java 会看 a **实际是啥**（Dog），然后调用 Dog 的 sound()。
>
> 生活中的例子：
> 你指着一只狗说"这是个动物"，然后让动物叫。
> 虽然你把它叫做动物，但它本质还是狗，叫出来的是"汪汪"不是"动物叫"。

---

### 19. 下面这段 Stream 代码做了什么？

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
numbers.stream()
       .filter(n -> n % 2 == 0)      // 第一步：筛选
       .map(n -> n * n)              // 第二步：转换
       .forEach(System.out::println); // 第三步：输出
```

> **答案：** 筛选出偶数，求平方，然后输出。输出结果为：
> ```
> 4
> 16
> 36
> ```
>
> **解析：**
> 这个过程像流水线：
>
> **原始数据**：[1, 2, 3, 4, 5, 6]（一堆数字）
>
> **第1步 .filter** → 筛选出偶数
> 条件：`n % 2 == 0`（整除2的留下）
> 结果：[2, 4, 6]（1、3、5 被筛掉了）
>
> **第2步 .map** → 每个数求平方
> 操作：`n * n`
> 结果：[4, 16, 36]
>
> **第3步 .forEach** → 逐个输出
> 输出：4、16、36

---

## 四、手写代码题（每题10分，共20分）

---

### 20. 写一个方法，接收一个字符串列表，返回其中长度大于3的字符串个数

要求：用 Stream 实现，一行搞定。

> **参考答案：**
> ```java
> public long countLongNames(List<String> list) {
>     return list.stream()                    // 把集合转成流
>                .filter(s -> s.length() > 3) // 只保留长度大于3的字符串
>                .count();                    // 统计个数
> }
> ```
>
> **解析：**
> 整个流程像流水线：
> 原始数据：["Tom", "Jerry", "Bob", "Alice", "An"]
> 经过 filter（长度>3）：["Jerry", "Alice"]  ← "Tom"=3不满足，"Bob"=3不满足，"An"=2不满足
> 经过 count：2
>
> **踩坑提示：**
> 注意方法返回类型是 `long` 不是 `int`，因为 Stream 的 count() 返回 long。

---

### 21. 写一个 Student 类，体现封装性。包含 name 和 score 两个私有属性，提供构造方法和 getter/setter

> **参考答案：**
> ```java
> public class Student {
>     // 私有属性——外部不能直接访问，体现了封装
>     private String name;   // 学生姓名
>     private double score;  // 学生成绩
>
>     // 构造方法——创建对象时一次性初始化所有属性
>     public Student(String name, double score) {
>         this.name = name;    // this.name 是成员变量，=右边的name是参数
>         this.score = score;
>     }
>
>     // getter 方法——外部通过这个方法获取属性值
>     public String getName() {
>         return name;  // 把私有属性返回给调用者
>     }
>
>     // setter 方法——外部通过这个方法修改属性值
>     public void setName(String name) {
>         this.name = name;  // 用传入的参数修改私有属性
>     }
>
>     public double getScore() {
>         return score;
>     }
>
>     public void setScore(double score) {
>         this.score = score;
>     }
> }
> ```
>
> **解析：**
> 封装的三步走：
> 1. 属性用 `private` —— 藏起来，外面看不见
> 2. 提供 `public` 的 getter —— 别人可以"看"但不能"直接改"
> 3. 提供 `public` 的 setter —— 别人通过这个方法"改"，可以在方法里加校验（比如分数不能是负数）
>
> **踩坑提示：**
> 构造方法里 `this.name = name` 的 `this` 不能省略！
> 不写 this 的话，`name = name` 是在自己给自己赋值，成员变量根本没变。

---

## 评分标准

| 分数 | 水平判断 | 建议 |
|------|---------|------|
| **90-100分** | ✅ JavaSE 非常扎实 | 直接进 MySQL |
| **75-89分** | ✅ 基础不错 | 进 MySQL，遇到不懂的回头补 |
| **60-74分** | ⚠️ 基础一般 | 把错题知识点再过一遍，然后进 MySQL |
| **40-59分** | 🔧 基础薄弱 | 推荐系统学一遍 JavaSE 再往下 |
| **0-39分** | ❌ 基础不够 | 从 JavaSE 零基础开始 |
