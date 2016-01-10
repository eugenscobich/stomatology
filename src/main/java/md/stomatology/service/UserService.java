package md.stomatology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.User;
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

}
