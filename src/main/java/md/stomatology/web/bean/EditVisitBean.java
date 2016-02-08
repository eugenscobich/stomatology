package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Allergy;
import md.stomatology.model.Visit;
import md.stomatology.model.PastIllnesse;
import md.stomatology.model.User;
import md.stomatology.service.AllergyService;
import md.stomatology.service.VisitService;
import md.stomatology.service.PastIllnesseService;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
@URLMapping(id = "edit-visit", pattern = "/view-customer/#{/[1-9][0-9]*/ customerId : editVisitBean.customerId}/edit-visit/#{/[0-9]+/ visitId : editVisitBean.visitId}", viewId = "/WEB-INF/views/pages/secure/edit-visit.xhtml")
public class EditVisitBean implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{visitService}")
	private transient VisitService visitService;
	
	@ManagedProperty(value = "#{viewCustomerBean}")
	private transient ViewCustomerBean viewCustomerBean;

	private Long customerId;
	
	private Long visitId;

	private Visit visit;

	@URLAction(onPostback = false)
	public String loadVisit() {
		LOG.info("loadVisit");
		if (visitId != null) {
			if (visitId != 0) {
				try {
					visit = visitService.getVisitById(visitId);
				} catch (Exception e) {
					WebUtil.addErrorMessage("could-not-load-visit-details", "error", e.getMessage());
					viewCustomerBean.setCustomerId(customerId);
					return "pretty:view-customer";
				}
			} else {
				visit = visitService.createNewVisit(customerId);
			}
			return null;
		}
		WebUtil.addWarnMessage("could-not-load-visit-details", "");
		viewCustomerBean.setCustomerId(customerId);
		return "pretty:view-customer";
	}

	public String save() {
		try {
			visitService.saveVisit(visit);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			WebUtil.addErrorMessage("could-not-save-visit", "error", new String[]{ e.getMessage() });
			return "";
		}
		WebUtil.addSuccessMessage("visit-has-saved-successfully");
		viewCustomerBean.setCustomerId(customerId);
		return "pretty:view-customer";
	}
	
	public VisitService getVisitService() {
		return visitService;
	}

	public void setVisitService(VisitService visitService) {
		this.visitService = visitService;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public ViewCustomerBean getViewCustomerBean() {
		return viewCustomerBean;
	}

	public void setViewCustomerBean(ViewCustomerBean viewCustomerBean) {
		this.viewCustomerBean = viewCustomerBean;
	}

}
