package com.alpha.self.diagnosis.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CustomMethodArgumentResolver extends AbstractArgumentResolver implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static Logger LOGGER = LoggerFactory.getLogger(CustomMethodArgumentResolver.class);

    /**
     * Basic constructor with converters only. Suitable for resolving
     * {@code @RequestBody}. For handling {@code @ResponseBody} consider also
     * providing a {@code ContentNegotiationManager}.
     */
    public CustomMethodArgumentResolver(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }


    /**
     * Basic constructor with converters and {@code ContentNegotiationManager}.
     * Suitable for resolving {@code @RequestBody} and handling
     * {@code @ResponseBody} without {@code Request~} or
     * {@code ResponseBodyAdvice}.
     */
    public CustomMethodArgumentResolver(List<HttpMessageConverter<?>> converters,
                                        ContentNegotiationManager manager) {

        super(converters, manager);
    }

    /**
     * Complete constructor for resolving {@code @RequestBody} method arguments.
     * For handling {@code @ResponseBody} consider also providing a
     * {@code ContentNegotiationManager}.
     *
     * @since 4.2
     */
    public CustomMethodArgumentResolver(List<HttpMessageConverter<?>> converters,
                                        List<Object> requestResponseBodyAdvice) {

        super(converters, null, requestResponseBodyAdvice);
    }

    /**
     * Complete constructor for resolving {@code @RequestBody} and handling
     * {@code @ResponseBody}.
     */
    public CustomMethodArgumentResolver(List<HttpMessageConverter<?>> converters,
                                        ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {

        super(converters, manager, requestResponseBodyAdvice);
    }


    @Override
    protected <T> Object readWithMessageConverters(NativeWebRequest webRequest, MethodParameter parameter,
                                                   Type paramType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);

        Object arg = readWithMessageConverters(inputMessage, parameter, paramType);
        if (arg == null) {
            if (checkRequired(parameter)) {
                throw new HttpMessageNotReadableException("Required request body is missing: " +
                        parameter.getMethod().toGenericString());
            }
        }
        return arg;
    }

    protected boolean checkRequired(MethodParameter parameter) {
        return true;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    public boolean supportsParameterMap(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
                String paramName = parameter.getParameterAnnotation(RequestParam.class).name();
                return StringUtils.hasText(paramName);
            } else {
                return true;
            }
        } else {
            if (parameter.hasParameterAnnotation(RequestPart.class)) {
                return false;
            }
            parameter = parameter.nestedIfOptional();
            if (MultipartResolutionDelegate.isMultipartArgument(parameter)) {
                return true;
            } else if (this.useDefaultResolution) {
                return BeanUtils.isSimpleProperty(parameter.getNestedParameterType());
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest.getContentType().contains("application/json")) {
            try {
                return resolveArgumentBody(parameter, mavContainer, webRequest, binderFactory);
            } catch (Exception e) {

            }
        }
        try {
            if (supportsParameterMap(parameter)) {
                return super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
            } else {
                return resolveArgumentAttribute(parameter, mavContainer, webRequest, binderFactory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Object resolveArgumentBody(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        parameter = parameter.nestedIfOptional();
        Object arg = readWithMessageConverters(webRequest, parameter, parameter.getNestedGenericParameterType());
        String name = Conventions.getVariableNameForParameter(parameter);

        WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
        if (arg != null) {
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
            }
        }
        mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
        return adaptArgumentIfNecessary(arg, parameter);
    }

    public Object resolveArgumentAttribute(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String name = ModelFactory.getNameForParameter(parameter);
        Object target = (mavContainer.containsAttribute(name)) ?
                mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, webRequest);
        WebDataBinder binder = binderFactory.createBinder(webRequest, target, name);
        if (binder.getTarget() != null) {
            bindRequestParameters(binder, webRequest);
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()) {
                if (isBindExceptionRequired(binder, parameter)) {
                    throw new BindException(binder.getBindingResult());
                }
            }
        }
        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);
        return binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
    }

    protected Object createAttribute(String attributeName, MethodParameter methodParam,
                                     WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        return BeanUtils.instantiateClass(methodParam.getParameterType());
    }

    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
    }


    private Type getHttpEntityType(MethodParameter parameter) {
        Assert.isAssignable(HttpEntity.class, parameter.getParameterType());
        Type parameterType = parameter.getGenericParameterType();
        if (parameterType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) parameterType;
            if (type.getActualTypeArguments().length != 1) {
                throw new IllegalArgumentException("Expected single generic parameter on '" +
                        parameter.getParameterName() + "' in method " + parameter.getMethod());
            }
            return type.getActualTypeArguments()[0];
        } else if (parameterType instanceof Class) {
            return Object.class;
        } else {
            return null;
        }
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
            throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

        mavContainer.setRequestHandled(true);
        ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);

        // Try even with null return value. ResponseBodyAdvice could get involved.
        writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
    }

    protected void validateIfApplicable(WebDataBinder binder, MethodParameter methodParam) {
        Annotation[] annotations = methodParam.getParameterAnnotations();
        for (Annotation ann : annotations) {
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
                Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                binder.validate(validationHints);
                break;
            }
        }
    }

    /**
     * Whether to raise a fatal bind exception on validation errors.
     *
     * @param binder      the data binder used to perform data binding
     * @param methodParam the method argument
     * @return {@code true} if the next method argument is not of type {@link Errors}
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter methodParam) {
        int i = methodParam.getParameterIndex();
        Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }


    private static final TypeDescriptor STRING_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(String.class);

    private boolean useDefaultResolution = true;


    /**
     * @param useDefaultResolution in default resolution mode a method argument
     *                             that is a simple type, as defined in {@link BeanUtils#isSimpleProperty},
     *                             is treated as a request parameter even if it isn't annotated, the
     *                             request parameter name is derived from the method parameter name.
     */
    public CustomMethodArgumentResolver(boolean useDefaultResolution) {
        this.useDefaultResolution = useDefaultResolution;
    }

    /**
     * @param beanFactory          a bean factory used for resolving  ${...} placeholder
     *                             and #{...} SpEL expressions in default values, or {@code null} if default
     *                             values are not expected to contain expressions
     * @param useDefaultResolution in default resolution mode a method argument
     *                             that is a simple type, as defined in {@link BeanUtils#isSimpleProperty},
     *                             is treated as a request parameter even if it isn't annotated, the
     *                             request parameter name is derived from the method parameter name.
     */
    public CustomMethodArgumentResolver(ConfigurableBeanFactory beanFactory, boolean useDefaultResolution) {
        super(beanFactory);
        this.useDefaultResolution = useDefaultResolution;
    }


    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
        return (ann != null ? new RequestParamNamedValueInfo(ann) : new RequestParamNamedValueInfo());
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        MultipartHttpServletRequest multipartRequest =
                WebUtils.getNativeRequest(servletRequest, MultipartHttpServletRequest.class);

        Object mpArg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
        if (mpArg != MultipartResolutionDelegate.UNRESOLVABLE) {
            return mpArg;
        }

        Object arg = null;
        if (multipartRequest != null) {
            List<MultipartFile> files = multipartRequest.getFiles(name);
            if (!files.isEmpty()) {
                arg = (files.size() == 1 ? files.get(0) : files);
            }
        }
        if (arg == null) {
            String[] paramValues = request.getParameterValues(name);
            if (paramValues != null) {
                arg = (paramValues.length == 1 ? paramValues[0] : paramValues);
            }
        }
        return arg;
    }

    @Override
    protected void handleMissingValue(String name, MethodParameter parameter, NativeWebRequest request)
            throws Exception {

        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        if (MultipartResolutionDelegate.isMultipartArgument(parameter)) {
            if (!MultipartResolutionDelegate.isMultipartRequest(servletRequest)) {
                throw new MultipartException("Current request is not a multipart request");
            } else {
                throw new MissingServletRequestPartException(name);
            }
        } else {
            throw new MissingServletRequestParameterException(name,
                    parameter.getNestedParameterType().getSimpleName());
        }
    }


    protected String formatUriValue(ConversionService cs, TypeDescriptor sourceType, Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return (String) value;
        } else if (cs != null) {
            return (String) cs.convert(value, sourceType, STRING_TYPE_DESCRIPTOR);
        } else {
            return value.toString();
        }
    }


    private static class RequestParamNamedValueInfo extends NamedValueInfo {

        public RequestParamNamedValueInfo() {
            super("", false, ValueConstants.DEFAULT_NONE);
        }

        public RequestParamNamedValueInfo(RequestParam annotation) {
            super(annotation.name(), annotation.required(), annotation.defaultValue());
        }
    }

}