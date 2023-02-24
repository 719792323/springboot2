# @Autowired和@Resource区别
1. @Autowired 是Spring提供的，@Resource 是J2EE提供的。
2. @Autowired只按type装配(Class),@Resource默认是按name装配。

# @Autowired和@Resource使用
1. @Autowired与@Resource都可以用来装配bean，都可以写在字段或setter方法上
2. @Autowired默认按类型装配，默认情况下必须要求依赖对象存在，如果要允许null值，
可以设置它的required属性为false。
如果想使用名称装配可以结合@Qualifier注解进行使用。
3. @Resource，默认按照名称进行装配，名称可以通过name属性进行指定，
如果没有指定name属性，当注解写在字段上时，
默认取字段名进行名称查找。如果注解写在setter方法上默认取属性名进行装配。
当找不到与名称匹配的bean时才按照类型进行装配。
** 但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配 **

# @Resource装配顺序

（1）如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常

（2）如果指定了name，则从Spring上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常

（3）如果指定了type，则从Spring上下文中找到类型匹配的唯一bean进行装配，找不到或找到多个，都抛出异常

（4）如果既没指定name，也没指定type,则自动按照byName方式进行装配。如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配。

@Resource的作用相当于@Autowired，只不过@Autowired按byType自动注入。
