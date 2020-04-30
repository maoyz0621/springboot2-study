1、 list

    list1: 1,2,3
    
    @Value("#{'${list1}'.split(',')}")
    private List<Integer> list1;
    
    对于list2:
        - 1
        - 2
        - 3
2、 map

    maps0: "{key1: 'value1', key2: 'value2'}"
    
    @Value("#{${maps0}}")
    private Map<String, String> maps0;