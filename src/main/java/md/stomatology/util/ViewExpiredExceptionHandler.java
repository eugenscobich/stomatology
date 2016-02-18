package md.stomatology.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

//ExceptionHandlerWrapper - JSF wrapper class which requires only to override getWrapped() method to return the
//instance of the class you're wrapping, which is often simply passed to the constructor
public class ViewExpiredExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler handler;

	public ViewExpiredExceptionHandler(ExceptionHandler handler) {
		this.handler = handler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return handler;
	}

	// once we override getWrapped(), we need only override those methods we're
	// interested in. In this case, we want to override only handle()
	@Override
	public void handle() throws FacesException {
		// iterate over unhandler exceptions using the iterator returned from
		// getUnhandledExceptionQueuedEvents().iterator()
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
			// The ExeceptionQueuedEvent is a SystemEvent from which we can get
			// the actual ViewExpiredException
			ExceptionQueuedEvent queuedEvent = i.next();
			ExceptionQueuedEventContext queuedEventContext = (ExceptionQueuedEventContext) queuedEvent.getSource();
			Throwable throwable = queuedEventContext.getException();
			if (throwable instanceof ViewExpiredException) {
				FacesContext fc = FacesContext.getCurrentInstance();
				RequestContext rc = RequestContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				try {
					//if (((rc != null && rc.isAjaxRequest())	|| (fc != null && fc.getPartialViewContext().isPartialRequest()))) {
						WebUtil.addErrorMessage("invalid-session");
						ec.redirect(ec.getRequestContextPath() + "/invalid-session");
						fc.responseComplete();
					//}
				} catch (IOException e) {
					System.out.println("Redirect to the specified page /login failed");
					throw new FacesException(e);
				} finally {
					// we call remove() on the iterator. This is an important
					// part of the ExceptionHandler usage contract.
					// If you handle an exception, you have to remove it from
					// the list of unhandled exceptions
					i.remove();
				}
			}
		}
		getWrapped().handle();
	}

}