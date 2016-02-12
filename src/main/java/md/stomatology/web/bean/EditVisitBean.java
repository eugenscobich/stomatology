package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
@URLMapping(id = "edit-visit", pattern = "/view-customer/#{/[1-9][0-9]*/ customerId : editVisitBean.customerId}/edit-visit/#{/[0-9]+/ visitId : editVisitBean.visitId}", viewId = "/WEB-INF/views/pages/secure/edit-visit.xhtml")
public class EditVisitBean implements Serializable {

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
		if (visitId != null) {
			if (visitId != 0) {
				try {
					visit = visitService.getVisitById(visitId);
				} catch (Exception e) {
					WebUtil.addErrorMessage("could-not-load-visit-details", "error", new String[] {e.getCause() + ": " + e.getMessage()});
					viewCustomerBean.setCustomerId(customerId);
					return "pretty:view-customer";
				}
			} else {
				User currentUser = WebUtil.getCurrentUser();
				visit = visitService.createNewVisit(customerId, currentUser);
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
	
	
	public void changeToothInfo() {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		String distressedSurfaces = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.distressedSurfaces}", String.class);
		Integer gumPocketsDepthTop = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.gumPocketsDepthTop}", Integer.class);
		Integer gumPocketsDepthRight = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.gumPocketsDepthRight}", Integer.class);
		Integer gumPocketsDepthBottom = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.gumPocketsDepthBottom}", Integer.class);
		Integer gumPocketsDepthLeft = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.gumPocketsDepthLeft}", Integer.class);
		
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				toothInfo.setDistressedSurfaces(distressedSurfaces);
				toothInfo.setGumPocketsDepthBottom(gumPocketsDepthBottom);
				toothInfo.setGumPocketsDepthRight(gumPocketsDepthRight);
				toothInfo.setGumPocketsDepthLeft(gumPocketsDepthLeft);
				toothInfo.setGumPocketsDepthTop(gumPocketsDepthTop);
				return;
			}
		}
	}
	
	public List<User> completeDentists(String query) {
		List<User> users = userService.getDentists(query, visit.getDentist());
		return users;
	}

	public void handleSelectDentist(SelectEvent event) {
		User dentist = (User) event.getObject();
		visit.setDentist(dentist);
	}
	
	public List<Disease> completeDisease(String query) {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				return diseaseService.getDiseases(query, toothInfo.getDiseases());
			}
		}
		return Collections.emptyList();
	}

	public void handleSelectDisease(SelectEvent event) {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		Disease disease = (Disease) event.getObject();
		
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				if (toothInfo.getDiseases() == null) {
					toothInfo.setDiseases(new ArrayList<>()); 
				}
				toothInfo.getDiseases().add(disease);
				return;
			}
		}
	}

	public void handleUnselectDisease(UnselectEvent event) {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		Disease disease = (Disease) event.getObject();
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				toothInfo.getDiseases().remove(disease);
				return;
			}
		}
	}

	public List<Treatment> completeTreatment(String query) {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				return treatmentService.getTreatments(query, toothInfo.getTreatments());
			}
		}
		return Collections.emptyList();
	}

	public void handleSelectTreatment(SelectEvent event) {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		Treatment treatment = (Treatment) event.getObject();
		
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				if (toothInfo.getTreatments() == null) {
					toothInfo.setTreatments(new ArrayList<>()); 
				}
				toothInfo.getTreatments().add(treatment);
				return;
			}
		}
	}

	public void handleUnselectTreatment(UnselectEvent event) {
		String toothIndex = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{toothInfo.index}", String.class);
		Treatment treatment = (Treatment) event.getObject();
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if(toothInfo.getIndex().equals(toothIndex)) {
				toothInfo.getTreatments().remove(treatment);
				return;
			}
		}
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
	
	public int[] getGumPocketDepthList() {
		int[] values = new int[10];
		for(int i = 0; i < 10; i++) {
			values[i] = i;
		}
		return values;
	}
}
