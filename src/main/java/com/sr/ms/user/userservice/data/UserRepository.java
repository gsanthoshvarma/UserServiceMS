package com.sr.ms.user.userservice.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
	
	public Optional<UserEntity> findByEmail(String email);
	
}
