package com.dev.accessrefreshtoken.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.accessrefreshtoken.model.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
	
	void deleteByOwner_Id(ObjectId id);
	
	default void deleteByOwner_Id(String id) {
		deleteByOwner_Id(new ObjectId(id));
	}

}
