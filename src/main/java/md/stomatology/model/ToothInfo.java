package md.stomatology.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import md.stomatology.model.type.ImplantType;

@Entity
@Table(name = "tooth_infos")
public class ToothInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	// 1..4
	@Column(name = "tooth_quadrant")
	private Integer toothQuadrant;
	
	// 1..8
	@Column(name = "tooth_index")
	private Integer toothIndex;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tooth_infos_to_diseases",
		      joinColumns={@JoinColumn(name="tooth_info_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="disease_id", referencedColumnName="id")})
	private List<Disease> diseases;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tooth_infos_to_treatments",
		      joinColumns={@JoinColumn(name="tooth_info_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="treatment_id", referencedColumnName="id")})
	private List<Treatment> treatments;
	
	@Column(name = "notes")
	private String notes;

	@Column(name = "distressed_surfaces")
	private String distressedSurfaces;
	
	@Column(name = "gum_pockets_depth_top")
	private Integer gumPocketsDepthTop;
	
	@Column(name = "gum_pockets_depth_right")
	private Integer gumPocketsDepthRight;
	
	@Column(name = "gum_pockets_depth_bottom")
	private Integer gumPocketsDepthBottom;
	
	@Column(name = "gum_pockets_depth_left")
	private Integer gumPocketsDepthLeft;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getToothIndex() {
		return toothIndex;
	}

	public void setToothIndex(Integer toothIndex) {
		this.toothIndex = toothIndex;
	}

	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}

	public Integer getToothQuadrant() {
		return toothQuadrant;
	}

	public void setToothQuadrant(Integer toothQuadrant) {
		this.toothQuadrant = toothQuadrant;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDistressedSurfaces() {
		return distressedSurfaces;
	}

	public void setDistressedSurfaces(String distressedSurfaces) {
		this.distressedSurfaces = distressedSurfaces;
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	} 
	
	public String getIndex(){
		return "" + toothQuadrant + toothIndex;
	}

	public Integer getGumPocketsDepthTop() {
		return gumPocketsDepthTop;
	}

	public void setGumPocketsDepthTop(Integer gumPocketsDepthTop) {
		this.gumPocketsDepthTop = gumPocketsDepthTop;
	}

	public Integer getGumPocketsDepthRight() {
		return gumPocketsDepthRight;
	}

	public void setGumPocketsDepthRight(Integer gumPocketsDepthRight) {
		this.gumPocketsDepthRight = gumPocketsDepthRight;
	}

	public Integer getGumPocketsDepthBottom() {
		return gumPocketsDepthBottom;
	}

	public void setGumPocketsDepthBottom(Integer gumPocketsDepthBottom) {
		this.gumPocketsDepthBottom = gumPocketsDepthBottom;
	}

	public Integer getGumPocketsDepthLeft() {
		return gumPocketsDepthLeft;
	}

	public void setGumPocketsDepthLeft(Integer gumPocketsDepthLeft) {
		this.gumPocketsDepthLeft = gumPocketsDepthLeft;
	}
	
	public String getDistressedSurfacesImageSrc() {
		StringBuilder imageSrc = new StringBuilder("distressed_surfaces_");
		if (toothIndex < 5) {
			imageSrc.append("4");
		} else if (toothIndex > 4) {
			imageSrc.append("5");
		}
		imageSrc.append("/");
		if (StringUtils.isNotBlank(distressedSurfaces)) {
			imageSrc.append(distressedSurfaces);
		} else {
			imageSrc.append("default");
		}
		imageSrc.append(".png");
		return imageSrc.toString();
	}

	public String getToothImageSrc() {
		StringBuilder imageSrc = new StringBuilder();
		
		ImplantType implantType = null;
		if (treatments != null) {
			for (Treatment treatment : treatments) {
				if (treatment.getImplantType() != null && implantType == null) {
					implantType = treatment.getImplantType(); 
				} else if (treatment.getImplantType() != null && treatment.getImplantType().compareTo(implantType) > 0) {
					implantType = treatment.getImplantType();
				}
			}
		}
		if (diseases != null) {
			for (Disease disease : diseases) {
				if (disease.getImplantType() != null && implantType == null) {
					implantType = disease.getImplantType(); 
				} else if (disease.getImplantType() != null && disease.getImplantType().compareTo(implantType) > 0) {
					implantType = disease.getImplantType();
				}
			}
		}
		if (implantType != null) {
			if (implantType == ImplantType.IMPLANT_AND_ABUTMENT_AND_UPPER_CROWN) {
				imageSrc.append(getIndex());
				imageSrc.append("/");
				imageSrc.append(implantType.toString());
			} else {
				imageSrc.append(implantType.toString());
				if (toothQuadrant < 3 ) {
					imageSrc.append("_top");
				} else if (toothQuadrant > 2) {
					imageSrc.append("_bottom");
				}
			}
		} else {
			imageSrc.append(getIndex());
			Boolean tratmentHasRemoved = treatments != null ? treatments.stream().anyMatch(treatment -> treatment.getIsRemoved()) : false;
			Boolean diseaseHasRemoved = diseases!= null ? diseases.stream().anyMatch(disease -> disease.getIsRemoved()) : false;
			imageSrc.append("/");
			if (tratmentHasRemoved || diseaseHasRemoved) {
				imageSrc.append("removed");
			} else {
				imageSrc.append("default");
			}
		}
		
		
		imageSrc.append(".png");
		return imageSrc.toString();
	}

	
	@Override
	public String toString() {
		return "Index: " + getIndex();
	}
	
}
