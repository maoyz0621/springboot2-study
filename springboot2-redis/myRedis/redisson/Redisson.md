## RBucket<I>对象桶

+ boolean compareAndSet(V expect, V update) 原子操作,如果当前值 == expect,则更新值为update

+ V getAndSet(V newValue) 原子操作,设置值为newValue,返回旧值

+ void set(V value)

+ boolean trySet(V value)

## RMap<K, V> 


## RedisTemplate和Redisson配置使用
注意 
```
    FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
    // 1. hash类型的key值
    template.setHashKeySerializer(fastJsonRedisSerializer);
    // 2. hash类型的key值
    template.setHashKeySerializer(new StringRedisSerializer());
```
在Redisson中Hash值的key为 "key" , 如果选择2, 在RedisTemplate中的key为 key,两者不能混用; 如果选择1, 则key为 "key",两者可以混用

## RAtomicLong和RLongAdder 原子类操作

+ add(long x)
+ increment()
+ decrement()

## RLock 锁