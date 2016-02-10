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

@Entity
@Table(name = "tooth_infos")
public class ToothInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name = "gum_pockets_depth")
	private String gumPocketsDepth;

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

	public String getGumPocketsDepth() {
		return gumPocketsDepth;
	}

	public void setGumPocketsDepth(String gumPocketsDepth) {
		this.gumPocketsDepth = gumPocketsDepth;
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
	
}
