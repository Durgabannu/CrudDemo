/*package com.sample.CrudDemo.Repository;

public interface TutorialsRepository {

}*/
package com.sample.CrudDemo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.CrudDemo.Model.Tutorials;


public interface TutorialsRepository extends JpaRepository<Tutorials,Long> {
	List<Tutorials> findBypublished(boolean published);
	List<Tutorials> findBytitle(String title);
	
}