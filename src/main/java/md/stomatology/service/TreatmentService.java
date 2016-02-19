package md.stomatology.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Treatment;
import md.stomatology.repository.TreatmentRepository;

@Service
public class TreatmentService {

	@Autowired
	private TreatmentRepository treatmentRepository;

    @Transactional(readOnly = true)
	public Treatment getTreatmentById(long id) {
		return treatmentRepository.findOne(id);
	}

    @Transactional(readOnly = true)
	public List<Treatment> getTreatments(String query, List<Treatment> treatments) {
    	List<Long> treatmentIds =  treatments != null && treatments.size() > 0 ? treatments.stream().map(Treatment::getId).collect(Collectors.toList()) : Arrays.asList(0l);
    	
		if(StringUtils.isNotBlank(query)) {
			return treatmentRepository.findByNameIgnoreCaseContainingAndIdNotIn(query, treatmentIds);
		} else {
			return treatmentRepository.findByIdNotIn(treatmentIds);
		}
	}
	
}
