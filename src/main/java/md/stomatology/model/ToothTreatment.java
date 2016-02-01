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
@Table(name = "tooth_treatment")
public class ToothTreatment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tooth_index")
	private String toothIndex;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tooth_treatments_to_diseases",
		      joinColumns={@JoinColumn(name="tooth_treatment_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="disease_id", referencedColumnName="id")})
	private List<Disease> diseases;
	
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

	public String getToothIndex() {
		return toothIndex;
	}

	public void setToothIndex(String toothIndex) {
		this.toothIndex = toothIndex;
	}

	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}

	public String getDistressedSurfaces() {
		return distressedSurfaces;
	}

	public void setDistressedSurfaces(String distressedSurfaces) {
		this.distressedSurfaces = distressedSurfaces;
	}

	public String getGumPocketsDepth() {
		return gumPocketsDepth;
	}

	public void setGumPocketsDepth(String gumPocketsDepth) {
		this.gumPocketsDepth = gumPocketsDepth;
	} 
	
}
