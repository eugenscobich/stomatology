package md.stomatology.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import md.stomatology.model.User;

/**
 * The Class WebUtil.
 */
public class WebUtil implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4327962222471753519L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(WebUtil.class);

	/** The Constant NULL_STRING. */
	private static final String NULL_STRING = "null";

	public static HttpServletRequest getHttpServletRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Object obj = facesContext.getExternalContext().getRequest();
		// how jsr329 bridge finds the request, another way is to dispatch to a
		// servlet or jsp
		if (obj instanceof HttpServletRequest) {
			HttpServletRequest pr = (HttpServletRequest) obj;
			return pr;
		}
		return null;
	}

	/**
	 * Get current portlet response.
	 *
	 * @return Portlet Response
	 */
	public static HttpServletResponse getHttpServletResponse() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Object obj = facesContext.getExternalContext().getResponse();
		// how jsr329 bridge finds the request, another way is to dispatch to a
		// servlet or jsp
		if (obj instanceof HttpServletResponse) {
			HttpServletResponse pr = (HttpServletResponse) obj;
			return pr;
		}
		return null;
	}

	/**
	 * Send Redirect.
	 *
	 * @param url
	 *            Url where to send redirect
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void sendRedirect(String url) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().redirect(url);
	}

	/**
	 * Get spring application context.
	 *
	 * @param httpServletRequest
	 *            Portlet request
	 * @return Application Context
	 */
	public static ApplicationContext getSpringAppContext(HttpServletRequest httpServletRequest) {
		return WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
	}

	/**
	 * Get bean from spring application context.
	 *
	 * @param <T>
	 *            the generic type
	 * @param httpServletRequest
	 *            Portlet Request
	 * @param beanName
	 *            Spring Bean Id
	 * @return Spring bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(HttpServletRequest httpServletRequest, String beanName) {
		return (T) getSpringAppContext(httpServletRequest).getBean(beanName);
	}

	/**
	 * Get Parameter value from request.
	 *
	 * @param <T>
	 *            the generic type
	 * @param parameterName
	 *            Name of parameter
	 * @param clazz
	 *            Return type of parameter String or Long
	 * @return Parameter value
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRequestParam(String parameterName, Class<T> clazz) {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String param = params.get(parameterName);
		if (StringUtils.isNotBlank(param)) {
			if (String.class.equals(clazz)) {
				return (T) param;
			} else if (Long.class.equals(clazz)) {
				return (T) Long.valueOf(param);
			} else if (Boolean.class.equals(clazz)) {
				return (T) Boolean.valueOf(param);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getRequestAttribute(String parameterName, Class<T> clazz) {
		HttpServletRequest httpRequest = getHttpServletRequest();
		Object param = httpRequest.getAttribute(parameterName);
		String strParam = param != null ? param.toString() : null;
		if (StringUtils.isNotBlank(strParam)) {
			if (String.class.equals(clazz)) {
				return (T) param;
			} else if (Long.class.equals(clazz)) {
				return (T) Long.valueOf(strParam);
			} else if (Boolean.class.equals(clazz)) {
				return (T) Boolean.valueOf(strParam);
			}
		}
		return null;
	}

	public static void putRequestAttribute(String parameterName, String value) {
		HttpServletRequest httpRequest = getHttpServletRequest();
		httpRequest.setAttribute(parameterName, value);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getUrlParam(String parameterName, Class<T> clazz) {
		HttpServletRequest httpRequest = getHttpServletRequest();
		String param = httpRequest.getParameter(parameterName);
		if (StringUtils.isNotBlank(param)) {
			if (String.class.equals(clazz)) {
				return (T) param;
			} else if (Long.class.equals(clazz)) {
				return (T) Long.valueOf(param);
			} else if (Boolean.class.equals(clazz)) {
				return (T) Boolean.valueOf(param);
			}
		}
		return null;
	}

	public static String getUrlParam(final String paramName) {
		HttpServletRequest httpRequest = getHttpServletRequest();
		return httpRequest.getParameter(paramName);
	}

	/**
	 * Add success message in faces context.
	 *
	 * @param messageCode
	 *            Bundle message code
	 * @param detailsCode
	 *            Bundle details code
	 * @param detailsParams
	 *            Details parameters
	 */
	public static void addSuccessMessage(String messageCode, String detailsCode, String... detailsParams) {
		addMessage(null, FacesMessage.SEVERITY_INFO, messageCode, detailsCode, detailsParams);
	}

	/**
	 * Adds the success message.
	 *
	 * @param messageCode
	 *            the message code
	 */
	public static void addSuccessMessage(String messageCode) {
		addMessage(null, FacesMessage.SEVERITY_INFO, messageCode, null);
	}

	/**
	 * Add worning message in faces context.
	 *
	 * @param messageCode
	 *            Bundle message code
	 * @param detailsCode
	 *            Bundle details code
	 * @param detailsParams
	 *            Details parameters
	 */
	public static void addWarnMessage(String messageCode, String detailsCode, String... detailsParams) {
		addMessage(null, FacesMessage.SEVERITY_WARN, messageCode, detailsCode, detailsParams);
	}

	/**
	 * Add error message in faces context.
	 *
	 * @param messageCode
	 *            Bundle message code
	 * @param detailsCode
	 *            Bundle details code
	 * @param detailsParams
	 *            Details parameters
	 */
	public static void addErrorMessage(String messageCode, String detailsCode, String... detailsParams) {
		addMessage(null, FacesMessage.SEVERITY_ERROR, messageCode, detailsCode, detailsParams);
	}

	/**
	 * Adds the error message.
	 *
	 * @param messageCode
	 *            the message code
	 */
	public static void addErrorMessage(String messageCode) {
		addMessage(null, FacesMessage.SEVERITY_ERROR, messageCode, null);
	}

	/**
	 * Adds the error message for element with id = clientId.
	 *
	 * @param messageCode
	 *            the message code
	 */
	public static void addErrorMessage(String messageCode, String clientId) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, messageCode, null);
	}

	/**
	 * Adds the error message for element with id = clientId.
	 *
	 * @param messageCode
	 *            the message code
	 */
	public static void addErrorMessage(String messageCode, String messageCodeDetails, String clientId) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, messageCode, messageCodeDetails);
	}

	/**
	 * Add fatal message in faces context.
	 *
	 * @param messageCode
	 *            Bundle message code
	 * @param detailsCode
	 *            Bundle details code
	 * @param detailsParams
	 *            Details parameters
	 */
	public static void addFatalMessage(String messageCode, String detailsCode, String... detailsParams) {
		addMessage(null, FacesMessage.SEVERITY_FATAL, messageCode, detailsCode, detailsParams);
	}

	/**
	 * Add message to faces context.
	 *
	 * @param severity
	 *            Severity of message
	 * @param messageCode
	 *            Bundle message code
	 * @param detailsMessageCode
	 *            Bundle details code
	 * @param detailsParams
	 *            Details parameters
	 */
	public static void addMessage(String clientId, Severity severity, String messageCode, String detailsMessageCode, String... detailsParams) {
		String fullClientId = null;
		if (StringUtils.isNotBlank(clientId)) {
			fullClientId = getComponentWithId(FacesContext.getCurrentInstance().getViewRoot(), clientId).getClientId();
		}
		FacesContext.getCurrentInstance().addMessage(fullClientId, getMessage(severity, messageCode, detailsMessageCode, detailsParams));
	}
	
	public static FacesMessage getMessage(Severity severity, String messageCode, String detailsMessageCode, String... detailsParams) {
		String message = getMessageValue(messageCode);
		String details = "";
		if (StringUtils.isNotBlank(detailsMessageCode)) {
			details = getMessageValue(detailsMessageCode);
			if (detailsParams != null && detailsParams.length > 0) {
				MessageFormat messageFormat = new MessageFormat(details);
				details = messageFormat.format(detailsParams);
			}
		}
		return new FacesMessage(severity, message, details);
	}
	

	/**
	 * Adds the message with params.
	 *
	 * @param severity
	 *            the severity
	 * @param messageCode
	 *            the message code
	 * @param messageParams
	 *            the message params
	 */
	public static void addMessageWithParams(Severity severity, String messageCode, Object... messageParams) {
		String message = getMessageValue(messageCode);
		String details = "";
		if (messageParams != null && messageParams.length > 0) {
			MessageFormat messageFormat = new MessageFormat(message);
			message = messageFormat.format(messageParams);
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, details));
	}

	/**
	 * Adds the success message.
	 *
	 * @param messageCode
	 *            the message code
	 * @param messageParams
	 *            the message params
	 */
	public static void addSuccessMessageWithParams(String messageCode, Object... messageParams) {
		addMessageWithParams(FacesMessage.SEVERITY_INFO, messageCode, messageParams);
	}

	/**
	 * Get message value.
	 *
	 * @param messageCode
	 *            Message code
	 * @return Message Value
	 */
	public static String getMessageValue(String messageCode) {
		Application application = FacesContext.getCurrentInstance().getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ValueExpression valueExpression = expressionFactory.createValueExpression(elContext,
				"#{msg['" + messageCode + "']}", String.class);
		String message = (String) valueExpression.getValue(elContext);
		return message;
	}

	/**
	 * Get Faces Component With Id
	 *
	 * @param parent
	 * @param id
	 * @return
	 */
	public static UIComponent getComponentWithId(UIComponent parent, String id) {
		for (Iterator<UIComponent> chs = parent.getFacetsAndChildren(); chs.hasNext();) {
			UIComponent ch = chs.next();
			if (ch.getId().equalsIgnoreCase(id)) {
				return ch;
			} else {
				UIComponent found = getComponentWithId(ch, id);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	/**
	 * Write stream to servlet response.
	 *
	 * @param inputStream
	 *            the input stream
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * public static void writeStreamToServletResponse(InputStream inputStream,
	 * String fileName) throws IOException { HttpServletResponse response =
	 * getHttpServletResponse(); setResponseHeaders(response, fileName);
	 * OutputStream out = response.getOutputStream(); IOUtils.copy(inputStream,
	 * out); }
	 * 
	 * private static void setResponseHeaders(HttpServletResponse response,
	 * String fileName) throws IOException {
	 * response.setHeader(HttpHeaders.CACHE_CONTROL,
	 * HttpHeaders.CACHE_CONTROL_PRIVATE_VALUE);
	 * response.setHeader(HttpHeaders.PRAGMA,
	 * HttpHeaders.PRAGMA_NO_CACHE_VALUE);
	 * response.setHeader("Content-Transfer-Encoding", "binary");
	 * response.setContentType("application/octet-stream");
	 * 
	 * if (StringUtils.isNotBlank(fileName)) { String contentDisposition =
	 * "attachment; filename=\"" + fileName + "\""; try { String encodedFileName
	 * = HttpUtil.encodeURL(fileName, true); HttpServletRequest request =
	 * PortalUtil.getHttpServletRequest(getHttpServletRequest()); if
	 * (BrowserSnifferUtil.isIe(request)) { contentDisposition =
	 * "attachment; filename=\"" + encodedFileName + "\""; } else {
	 * contentDisposition = "attachment; filename*=UTF-8''" + encodedFileName; }
	 * 
	 * } catch (Exception e) { LOGGER.error(e); }
	 * response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
	 * } response.flushBuffer(); }
	 */

	/**
	 * Read build version from manifest.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getAppBuildVersion() throws IOException {

		Manifest manifest = new Manifest();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		manifest.read(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF"));
		Attributes attributes = manifest.getMainAttributes();

		return attributes.getValue("Implementation-Build");
	}

	public static <T> T getManagedBean(String serviceName, Class<T> clazz) throws IllegalArgumentException {
		if (StringUtils.isEmpty(serviceName) || clazz == null) {
			return null;
		}
		FacesContext facContext = FacesContext.getCurrentInstance();

		if (facContext == null || facContext.getApplication() == null) {
			return null;
		}
		ELResolver elResolver = facContext.getApplication().getELResolver();
		ELContext elContext = facContext.getELContext();

		if (elResolver == null || elContext == null) {
			return null;
		}
		Object managedBean = elResolver.getValue(elContext, null, serviceName);

		if (!elContext.isPropertyResolved()) {
			System.out.println("Property Not Resolved");
		}

		if (managedBean == null) {
			return null;
		} else {
			if (clazz.isInstance(managedBean)) {
				return clazz.cast(managedBean);
			} else {
				System.out.println("Expected type is: [" + clazz.getName() + "] | Actual type is: ["
						+ managedBean.getClass().getName() + "]");
				return null;
			}
		}
	}

	public static User getCurrentUser() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
