package md.stomatology.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Allergy;
import md.stomatology.repository.AllergyRepository;

@Service
public class AllergyService {

	@Autowired
	private AllergyRepository allergyRepository;

    @Transactional(readOnly = true)
	public Allergy getAllergyById(long parseLong) {
		return allergyRepository.findOne(parseLong);
	}

    @Transactional(readOnly = true)
	public List<Allergy> getAllergies(String query, List<Allergy> allergies) {
    	List<Long> allergyIds =  allergies != null && allergies.size() > 0 ? allergies.stream().map(Allergy::getId).collect(Collectors.toList()) : Arrays.asList(0l);
    	
		if(StringUtils.isNotBlank(query)) {
			return allergyRepository.findByNameIgnoreCaseContainingAndIdNotIn(query, allergyIds);
		} else {
			return allergyRepository.findByIdNotIn(allergyIds);
		}
	}
	
}
