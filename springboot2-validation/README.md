## 区别

@NotNull 任何对象的value不能为null

@NotEmpty  集合对象的元素不为0，即集合不为空，也可以用于字符串不为null

@NotBlank  只能用于字符串不为null，并且字符串trim()以后length要大于0

## @Validated和@Valid

两者作用基本相等

## @Validated和@Valid注解附带BindingResult

+ 绑定BindingResult

```
    // 校验结果捕获
    if (bindingResult.hasErrors()) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach((error) -> {
            sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(" ;");
        });
        logger.error("************ {} *************", sb);
        Result errorResult = new Result();
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }
```

+ 没有绑定BindingResult

```
@ControllerAdvice
public class BindExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BindExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex) {
        Result errorResult = new Result();
        if (ex instanceof BindException) {
            handleBindException((BindException) ex, errorResult);
        }

        return errorResult;
    }

    /**
     * BindException专门用来处理数据检验validation异常
     */
    private Result handleBindException(BindException ex, Result errorResult) {
        // ex.getFieldError():随机返回一个对象属性的异常信息
        // 如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()

        // 获取绑定结果
        BindingResult bindingResult = ex.getBindingResult();

        logger.debug("数据校验错误量 = {}", bindingResult.getErrorCount());

        // 方法1
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 方法2
        List<FieldError> allErrors = ex.getFieldErrors();
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : allErrors) {
            sb.append(fieldError.getField())
                    .append(" = [")
                    .append(fieldError.getRejectedValue())
                    .append("] ")
                    .append(fieldError.getDefaultMessage())
                    .append(" ; ");
        }

        // 生成返回结果
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }
}
```

+ 使用import javax.validation.Validator自行校验

```
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Result validator(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        // constraintViolation.getPropertyPath()获取属性名, constraintViolation.getMessage()获取错误信息
        constraintViolations.forEach((constraintViolation) ->
                sb.append(constraintViolation.getPropertyPath())
                        .append(" : ")
                        .append(constraintViolation.getMessage())
                        .append(" ;"));

        logger.error("************************ {} *************************", sb);
        Result errorResult = new Result();
        errorResult.setCode(10000);
        errorResult.setMessage(JSONObject.toJSONString(sb));
        return errorResult;
    }

```

## 自定义检验注解

通过implements ConstraintValidator<A extends Annotation, T> 实现

example:
```
@Documented
// 指定真正实现校验规则的类
@Constraint(validatedBy = PhoneValidationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneValidation {

    String message() default "请输入正确的手机号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PhoneValidation[] value();
    }

}


/**
 * 真正执行校验的类，对输入的手机执行校验
 */
public class PhoneValidationValidator implements ConstraintValidator<PhoneValidation, String> {

    private static final Logger logger = LoggerFactory.getLogger(PhoneValidationValidator.class);

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );


    @Override
    public void initialize(PhoneValidation constraintAnnotation) {
        logger.debug("start PhoneValidationValidator initialize()" );
    }

    /**
     *
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("start PhoneValidationValidator isValid(), value = {}", value );
        logger.debug("context = {}", context);
        if (value == null || value.length() == 0){
            return true;
        }

        Matcher matcher = PHONE_PATTERN.matcher(value);
        return matcher.matches();
    }
}

```