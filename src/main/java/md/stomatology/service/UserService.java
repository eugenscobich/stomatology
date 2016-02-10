package md.stomatology.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.User;
import md.stomatology.model.type.AuthorityType;
import md.stomatology.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		User user = userRepository.findByUsername(username);
		user.getAuthorities().size();
		return user;
	}

	@Transactional(readOnly = true)
	public User getUserById(Long userId) {
		return userRepository.findOne(userId);
	}

	public List<User> getDentists(String query, User dentist) {
		Long userId = dentist != null ? dentist.getId() : 0l;
    	
		if(StringUtils.isNotBlank(query)) {
			return userRepository.findAllDentists("%" + query + "%", AuthorityType.ROLE_DENTIST, userId);
		} else {
			return userRepository.findAllDentists(AuthorityType.ROLE_DENTIST, userId);
		}
	}

}
