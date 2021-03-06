package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Allergy;
import md.stomatology.model.Disease;
import md.stomatology.model.Visit;
import md.stomatology.model.PastIllnesse;
import md.stomatology.model.ToothInfo;
import md.stomatology.model.Treatment;
import md.stomatology.model.User;
import md.stomatology.service.AllergyService;
import md.stomatology.service.DiseaseService;
import md.stomatology.service.VisitService;
import md.stomatology.service.PastIllnesseService;
import md.stomatology.service.TreatmentService;
import md.stomatology.service.UserService;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
@URLMapping(id = "view-visit", pattern = "/view-customer/#{/[1-9][0-9]*/ customerId : viewVisitBean.customerId}/view-visit/#{/[1-9][0-9]*/ visitId : viewVisitBean.visitId}", viewId = "/WEB-INF/views/pages/secure/view-visit.xhtml")
public class ViewVisitBean implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{visitService}")
	private transient VisitService visitService;

	@ManagedProperty(value = "#{viewCustomerBean}")
	private transient ViewCustomerBean viewCustomerBean;

	@ManagedProperty(value = "#{userService}")
	private transient UserService userService;

	@ManagedProperty("#{diseaseService}")
	private transient DiseaseService diseaseService;

	@ManagedProperty("#{treatmentService}")
	private transient TreatmentService treatmentService;

	private Long customerId;

	private Long visitId;

	private Visit visit;

	@URLAction(onPostback = false)
	public String loadVisit() {
		LOG.info("loadVisit");
		try {
			visit = visitService.getVisitById(visitId);
		} catch (Exception e) {
			WebUtil.addErrorMessage("could-not-load-visit-details", "error",
					new String[] { e.getCause() + ": " + e.getMessage() });
			viewCustomerBean.setCustomerId(customerId);
			return "pretty:view-customer";
		}
		return null;
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public DiseaseService getDiseaseService() {
		return diseaseService;
	}

	public void setDiseaseService(DiseaseService diseaseService) {
		this.diseaseService = diseaseService;
	}

	public TreatmentService getTreatmentService() {
		return treatmentService;
	}

	public void setTreatmentService(TreatmentService treatmentService) {
		this.treatmentService = treatmentService;
	}

}
