package md.stomatology.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTimeoutListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private static Logger LOG = LoggerFactory.getLogger(SessionTimeoutListener.class);

	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		RequestContext rc = RequestContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) ec.getResponse();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		if (ec.isResponseCommitted()) {
			return;
		}
		
		if (request.getRequestURL().toString().contains("spring-security-invalid-session")) {
			try {
				if (((rc != null && rc.isAjaxRequest())
						|| (fc != null && fc.getPartialViewContext().isPartialRequest()))
						&& fc.getResponseWriter() == null && fc.getRenderKit() == null) {
					
					response.setCharacterEncoding(request.getCharacterEncoding());
					RenderKitFactory factory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
					RenderKit renderKit = factory.getRenderKit(fc, fc.getApplication().getViewHandler().calculateRenderKitId(fc));

					ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null, request.getCharacterEncoding());
					fc.setResponseWriter(responseWriter);
					//WebUtil.addErrorMessage("invalid-session");
					ec.redirect(ec.getRequestContextPath() + "/spring-security-invalid-session");
				}

			} catch (IOException e) {
				System.out.println("Redirect to the specified page /login failed");
				throw new FacesException(e);
			}
		} else {
			return;
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}