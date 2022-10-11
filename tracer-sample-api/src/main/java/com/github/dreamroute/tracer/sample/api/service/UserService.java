package com.github.dreamroute.tracer.sample.api.service;

import com.github.dreamroute.tracer.sample.api.domain.User;

public interface UserService {

    User selectById(Long id);

}
