# 注意点
1. :后面有一个空格
2. 在yaml字符串无特性情况不用使用""或''包括，但是如果用''里面的转义字符会被视为普通字符串，如果用""里面的依旧认为是转义字符。

* 字面量：单个的、不可再分的值。date、boolean、string、number、null
  k: v

* 对象：键值对的集合。map、hash、set、object 
```yaml
    行内写法：  k: {k1:v1,k2:v2,k3:v3}
    #或
    k: 
      k1: v1
      k2: v2
      k3: v3
```

* 数组：一组按次序排列的值。array、list、queue
```yaml
    行内写法：  k: [v1,v2,v3]
    #或者
    k:
     - v1
     - v2
     - v3
```

yaml与java对象映射举例
```java
@Data
public class Person {
	
	private String userName;
	private Boolean boss;
	private Date birth;
	private Integer age;
	private Pet pet;
	private String[] interests;
	private List<String> animal;
	private Map<String, Object> score;
	private Set<Double> salarys;
	private Map<String, List<Pet>> allPets;
}

@Data
public class Pet {
	private String name;
	private Double weight;
}
```

```yaml
# yaml表示以上对象
person:
  userName: zhangsan
  boss: false
  birth: 2019/12/12 20:12:33
  age: 18
  pet: 
    name: tomcat
    weight: 23.4
  interests: [篮球,游泳]
  animal: 
    - jerry
    - mario
  score:
    english: 
      first: 30
      second: 40
      third: 50
    math: [131,140,148]
    chinese: {first: 128,second: 136}
  salarys: [3999,4999.98,5999.99]
  allPets:
    sick:
      - {name: tom}
      - {name: jerry,weight: 47}
    health: [{name: mario,weight: 47}]
```