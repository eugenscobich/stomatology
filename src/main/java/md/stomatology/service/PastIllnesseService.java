package md.stomatology.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.PastIllnesse;
import md.stomatology.repository.PastIllnesseRepository;

@Service
public class PastIllnesseService {

	@Autowired
	private PastIllnesseRepository pastIllnesseRepository;

    @Transactional(readOnly = true)
	public PastIllnesse getPastIllnesseById(long parseLong) {
		return pastIllnesseRepository.findOne(parseLong);
	}

    @Transactional(readOnly = true)
	public List<PastIllnesse> getPastIllnesses(String query, List<PastIllnesse> pastIllnesses) {
    	List<Long> pastIllnesseIds = pastIllnesses != null && pastIllnesses.size() > 0 ? pastIllnesses.stream().map(PastIllnesse::getId).collect(Collectors.toList()) : Arrays.asList(0l);

    	if(StringUtils.isNotBlank(query)) {
			return pastIllnesseRepository.findByNameIgnoreCaseContainingAndIdNotIn(query, pastIllnesseIds);
		} else {
			return pastIllnesseRepository.findByIdNotIn(pastIllnesseIds);
		}
	}
	
}
