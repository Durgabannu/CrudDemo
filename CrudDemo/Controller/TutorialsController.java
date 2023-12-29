/*package com.sample.CrudDemo.Controller;

public class TutorialsController {

}*/
package com.sample.CrudDemo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.CrudDemo.Model.Tutorials;
import com.sample.CrudDemo.Repository.TutorialsRepository;



@RestController
@RequestMapping("/Api")
public class TutorialsController {
	@Autowired
	   TutorialsRepository tutorialsRepository; 
	   @GetMapping("/tutorials")
	   public ResponseEntity<List<Tutorials>> getALLTutorials(@RequestParam(required=false)String title)
	   {
		  try {
			  List<Tutorials>tutorials=new ArrayList<Tutorials>();
			   if(title==null)
			   tutorialsRepository.findAll().forEach(tutorials::add);
			   else
			   tutorialsRepository.findBytitle(title).forEach(tutorials::add);
			   if(tutorials.isEmpty())
			   {
			   return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			   }
			   else {
			     return new ResponseEntity<>(tutorials,HttpStatus.OK);
			   }
		  }
		  catch(Exception e) {
			  return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	   @PostMapping("/tutorialscreate")
	    public ResponseEntity<Tutorials> createTutorial(@RequestBody Tutorials tutorial)
	    {
	        try {
	            Tutorials _tutorials = tutorialsRepository.save(new Tutorials(0, tutorial.getTitle(),tutorial.getDescription(),false));
	            return new ResponseEntity<>(_tutorials,HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	   @GetMapping("/tutorials/{id}")
	   public ResponseEntity<Tutorials>getTutorialById(@PathVariable("id")long id)
	   {
		   java.util.Optional<Tutorials> tutorialData=tutorialsRepository.findById(id);
		   if(tutorialData.isPresent())
		   {
			   return new ResponseEntity<>(tutorialData.get(),HttpStatus.OK);  
		   }
		   else {
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		   }
	   }
	   @PutMapping("/tutorials/{id}")
	    public ResponseEntity<Tutorials> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorials tutorial)
	   {
	        Optional<Tutorials> tutorialData = tutorialsRepository.findById(id);
	        if(tutorialData.isPresent()){
	            Tutorials _tutorial= tutorialData.get();
	            _tutorial.setTitle(tutorial.getTitle());
	            _tutorial.setDescription(tutorial.getDescription());
	            _tutorial.setPublished(tutorial.isPublished());
	            return new ResponseEntity<>(tutorialsRepository.save(_tutorial),HttpStatus.OK);
	        }
	        else{
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	   }
	   
	  @DeleteMapping("/tutorials/{id}")
	    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id){
	        try {
	            tutorialsRepository.deleteById(id);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    @DeleteMapping("/tutorials")
	    public ResponseEntity<HttpStatus> deleteAllTutorials()
	    {
	        try {
	            tutorialsRepository.deleteAll();
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	   
}

	   
	   