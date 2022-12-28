package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.entity.Datecourse;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional // @Modifying이 일어난 메소드가 실행된 후 바로 트랜잭션이 일어날 수 있도록 / Repository 자체를 @Transactional로 선언
public interface DatecourseRepository extends JpaRepository<Datecourse, Integer> {

}
