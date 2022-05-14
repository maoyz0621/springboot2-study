/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.validate.config;

import com.myz.config.enable.validate.SimpleValidateService;
import com.myz.config.enable.validate.ValidateService;
import com.myz.config.enable.validate.ValidateType;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * @author maoyz0621 on 2021/2/4
 * @version v1.0
 */
public abstract class ValidateImportSelector<A extends Annotation> implements ImportSelector {

    public static final String DEFAULT_VALIDATE_TYPE_ATTRIBUTE_NAME = "policy";

    protected String getValidateTypeAttributeName() {
        return DEFAULT_VALIDATE_TYPE_ATTRIBUTE_NAME;
    }

    /**
     * 导入到容器中的组件
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 解析当前类使用的泛型
        // interface com.myz.config.enable.validate.config.EnableValidate
        Class<?> annType = GenericTypeResolver.resolveTypeArgument(getClass(), ValidateImportSelector.class);
        Assert.state(annType != null, "Unresolvable type argument for ValidateImportSelector");

        AnnotationAttributes annotationAttribute = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(
                        EnableValidate.class.getName()));

        if (annotationAttribute == null) {
            throw new IllegalArgumentException(String.format(
                    "@%s is not present on importing class '%s' as expected",
                    annType.getSimpleName(), importingClassMetadata.getClassName()));
        }

        // 在这里可以拿到所有注解的信息，可以根据不同注解的和注解的属性来返回不同的class,
        // 从而达到开启不同功能的目的
        Class<ValidateService> policyClass = (Class<ValidateService>) annotationAttribute.getClass("policyClass");
        ValidateType policy = annotationAttribute.getEnum(getValidateTypeAttributeName());
        if (annotationAttribute.getBoolean("isOpen")) {
            String[] imports = selectImports(policy);
            if (imports == null) {
                throw new IllegalArgumentException("Unknown ValidateType: " + policy);
            }
            return imports;
        }
        return new String[]{SimpleValidateService.class.getName()};
    }

    @Nullable
    protected abstract String[] selectImports(ValidateType validateType);
}