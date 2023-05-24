# XxxWrapper



## select使用自定义sql语句：

`LambdaQueryWrapper.apply( .apply(true, "age is not null and FIND_IN_SET (',', age)"))`，
执行的sql语句为：`WHERE (age IS NOT NULL AND FIND_IN_SET(',', age) ...`



## update

```java
int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper)
```

- entity  实体对象 (set 条件值，可以为 null)
- updateWrapper 实体对象封装操作类（可以为 null，里面的 entity 用于生成 where 语句）

> entity设置的null不更新，updateWrapper设置的null做更新

### Wrapper

```java
// 重新赋值,null值不更新
UserEntity entity = new UserEntity().setAge(11).setEmail("a1").setGrade(null);

// where条件
UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate(entityWhere);

userMapper.update(entity, updateWrapper);
```
执行sql：`UPDATE user SET age = 11, email = "a1" WHERE age = 10 AND email = "a"`



### UpdateWrapper

```java
// where条件
UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>(entityWhere);

// UpdateWrapper赋值set
updateWrapper.set("created_by", "maoyz");
updateWrapper.set("age", 10);
updateWrapper.set("email", null);
userMapper.update(null, updateWrapper);
```

执行sql：`UPDATE user SET created_by = "maoyz", age = 10, email = null WHERE age = 10 AND email = "a" `



### LambdaUpdateWrapper

```java
// where条件
UserEntity entityWhere = new UserEntity().setAge(10);
LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate(entityWhere);

// 1、LambdaUpdateWrapper拼接where 条件
updateWrapper.eq(UserEntity::getEmail, "a");

// 2、LambdaUpdateWrapper设置值set
updateWrapper.set(UserEntity::getCompanyId, 100000);
updateWrapper.set(UserEntity::getEmail, null);
userMapper.update(null, updateWrapper);
```

执行sql：`UPDATE user SET company_id = 100000, email = null WHERE age = 10 AND (email = "a") `



## updateById

```java
UserEntity entity = new UserEntity().setId(1L).setAge(10).setEmail("a");
userMapper.updateById(entity);
```

执行sql：`UPDATE user SET age = 10, email = "a" WHERE id = 1`