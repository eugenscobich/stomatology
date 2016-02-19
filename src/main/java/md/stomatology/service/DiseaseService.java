package md.stomatology.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Disease;
import md.stomatology.repository.DiseaseRepository;

@Service
public class DiseaseService {

	@Autowired
	private DiseaseRepository diseaseRepository;

    @Transactional(readOnly = true)
	public Disease getDiseaseById(long id) {
		return diseaseRepository.findOne(id);
	}

    @Transactional(readOnly = true)
	public List<Disease> getDiseases(String query, List<Disease> diseases) {
    	List<Long> diseaseIds =  diseases != null && diseases.size() > 0 ? diseases.stream().map(Disease::getId).collect(Collectors.toList()) : Arrays.asList(0l);
    	
		if(StringUtils.isNotBlank(query)) {
			return diseaseRepository.findByNameIgnoreCaseContainingAndIdNotIn(query, diseaseIds);
		} else {
			return diseaseRepository.findByIdNotIn(diseaseIds);
		}
	}
	
}
