# 06 - 常用API（⭐⭐ 开发天天用）

## 一、StringBuilder（字符串拼接效率最高）

```java
// String 是不可变的，每次拼接都创建新对象
String s = "";
for (int i = 0; i < 1000; i++) {
    s += i;  // 创建了1000个新字符串对象！效率极低
}

// StringBuilder 是可变的，拼接不创建新对象
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);  // 在原有对象上追加，效率高
}
String result = sb.toString();  // 最后转成 String
```

## 二、Arrays 工具类

```java
int[] arr = {3, 1, 4, 1, 5, 9};

Arrays.sort(arr);              // 排序：[1, 1, 3, 4, 5, 9]
Arrays.toString(arr);          // 转字符串：[1, 1, 3, 4, 5, 9]
int index = Arrays.binarySearch(arr, 4);  // 二分查找，返回索引3
int[] copy = Arrays.copyOf(arr, 3);      // 复制前3个元素：[1, 1, 3]
```

## 三、Collections 工具类

```java
List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));

Collections.sort(list);         // 排序
Collections.reverse(list);      // 反转
Collections.shuffle(list);      // 随机打乱
Collections.max(list);          // 最大值
Collections.min(list);          // 最小值
```

## 四、日期时间 API

```java
// 旧版 Date（不推荐，很多方法已废弃）
Date date = new Date();
System.out.println(date);  // 当前时间

// Java8 新版日期 API（推荐）
LocalDate today = LocalDate.now();                    // 当前日期：2026-07-16
LocalTime now = LocalTime.now();                      // 当前时间：14:30:00
LocalDateTime dt = LocalDateTime.now();               // 当前日期时间
LocalDate birthday = LocalDate.of(2000, 1, 1);       // 指定日期

// 格式化
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String formatted = dt.format(formatter);              // 日期转字符串
LocalDateTime parsed = LocalDateTime.parse("2026-07-16 14:30:00", formatter); // 字符串转日期
```
